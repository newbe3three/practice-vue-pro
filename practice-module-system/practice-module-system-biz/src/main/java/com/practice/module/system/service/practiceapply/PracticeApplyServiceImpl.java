package com.practice.module.system.service.practiceapply;

import com.practice.module.system.controller.admin.practice.vo.practice.PracticeIdPageReqVO;
import com.practice.module.system.controller.admin.practiceapply.vo.apply.PracticeApplyCreateReqVO;
import com.practice.module.system.controller.admin.practiceapply.vo.apply.PracticeApplyExportReqVO;
import com.practice.module.system.controller.admin.practiceapply.vo.apply.PracticeApplyPageReqVO;
import com.practice.module.system.controller.admin.practiceapply.vo.apply.PracticeApplyUpdateReqVO;
import com.practice.module.system.controller.admin.practiceapply.vo.reject.PracticeApplyRejectCreateReqVO;
import com.practice.module.system.convert.practiceapply.PracticeApplyRejectConvert;
import com.practice.module.system.dal.dataobject.practice.PracticeDO;
import com.practice.module.system.dal.dataobject.practiceapply.PracticeApplyRejectDO;
import com.practice.module.system.dal.mysql.practice.PracticeMapper;
import com.practice.module.system.dal.mysql.practiceapply.PracticeApplyRejectMapper;
import com.practice.module.system.service.practice.PracticeService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import com.practice.module.system.dal.dataobject.practiceapply.PracticeApplyDO;
import com.practice.framework.common.pojo.PageResult;

import com.practice.module.system.convert.practiceapply.PracticeApplyConvert;
import com.practice.module.system.dal.mysql.practiceapply.PracticeApplyMapper;

import static com.practice.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.practice.module.system.enums.ErrorCodeConstants.*;

/**
 * 实践申请 Service 实现类
 *
 * @author n3
 */
@Service
@Validated
public class PracticeApplyServiceImpl implements PracticeApplyService {

    @Resource
    private PracticeApplyMapper practiceApplyMapper;
    @Resource
    private PracticeMapper practiceMapper;
    @Resource
    private PracticeApplyRejectMapper rejectMapper;
    @Resource
    private PracticeService practiceService;


    @Override
    public Long createPracticeApply(PracticeApplyDO practiceApply) {

        //检验 实践 是否存在， 同时检验实践状态是否为 征集中 2
        if(practiceMapper.selectById(practiceApply.getPracticeId()) == null) {
            throw exception(PRACTICE_NOT_EXISTS);
        }
        if(practiceMapper.selectById(practiceApply.getPracticeId()).getStatus() != 2) {
            throw exception(PRACTICE_STATUS_ERROR);
        }
        //检验申请是否重复，同一个用户对同意实践只能申请一次
        if(practiceApplyMapper.selectWithUserIdAndPracticeId(
                practiceApply.getUserId(),practiceApply.getPracticeId()) != null) {
            throw exception(PRACTICE_APPLY_REPEAT);
        }
        //发起申请时，默认设置申请的状态为待审核 0
        practiceApply.setStatus((byte) 0);
        // 插入
        practiceApplyMapper.insert(practiceApply);
        // 返回
        return practiceApply.getId();
    }

    @Override
    public void updatePracticeApply(PracticeApplyDO updateDo) {
        // 校验存在
        validatePracticeApplyExists(updateDo.getId());
        // 验证 实践的状态
        if(practiceMapper.selectById(updateDo.getPracticeId()).getStatus() != 2) {
            throw exception(PRACTICE_STATUS_ERROR);
        }
        // 更新
        updateDo.setStatus((byte) 0);
        practiceApplyMapper.updateById(updateDo);
    }

    @Override
    public void deletePracticeApply(Long id) {
        // 校验存在
        validatePracticeApplyExists(id);
        // 删除
        practiceApplyMapper.deleteById(id);
    }

    private void validatePracticeApplyExists(Long id) {
        if (practiceApplyMapper.selectById(id) == null) {
            throw exception(PRACTICE_APPLY_NOT_EXISTS);
        }
    }

    private void validatePracticeApplyStatus(Long id) {
        if (practiceApplyMapper.selectById(id).getStatus() != 0) {
            throw exception(PRACTICE_APPLY_STATUS_ERROR);
        }
    }
    @Override
    public PracticeApplyDO getPracticeApply(Long id) {
        return practiceApplyMapper.selectById(id);
    }

    @Override
    public List<PracticeApplyDO> getPracticeApplyList(Collection<Long> ids) {
        return practiceApplyMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<PracticeApplyDO> getPracticeApplyPage(PracticeApplyPageReqVO pageReqVO) {
        return practiceApplyMapper.selectPage(pageReqVO);
    }

    @Override
    public List<PracticeApplyDO> getPracticeApplyList(PracticeApplyExportReqVO exportReqVO) {
        return practiceApplyMapper.selectList(exportReqVO);
    }

    @Override
    public List<PracticeApplyRejectDO> getPracticeApplyRejectListByPracticeApplyId(Long practiceApplyId) {
        //验证申请是否存在
        validatePracticeApplyExists(practiceApplyId);

        return  rejectMapper.selectList("apply_id",practiceApplyId);
    }

    @Override
    public void reviewPassPracticeApply(Long practiceApplyId) {
        PracticeApplyDO practiceApplyDO;
        try {
            // 验证申请存在性
            practiceApplyDO = practiceApplyMapper.selectById(practiceApplyId);

        }catch (Exception e) {
            throw exception(PRACTICE_APPLY_NOT_EXISTS);
        }
        // 验证申请的状态是否为待审核 0
        if (practiceApplyDO.getStatus() != 0) {
            throw exception(PRACTICE_APPLY_STATUS_ERROR);
        }

        //验证是否存在
        //validatePracticeApplyExists(practiceApplyId);
       //验证状态是否为待审核
        //validatePracticeApplyStatus(practiceApplyId);
        //PracticeApplyDO practiceApplyDO = practiceApplyMapper.selectById(practiceApplyId);
        // 审核通过修改状态
        practiceApplyDO.setStatus((byte) 1);
        practiceApplyMapper.updateById(practiceApplyDO);

        //修改需要人数
        PracticeDO practiceDO = practiceMapper.selectById(practiceApplyDO.getPracticeId());

        practiceDO.setNumberPeople(practiceDO.getNumberPeople()-1);
        practiceMapper.updateById(practiceDO);
        // 如果人数 == 0
        if (practiceDO.getNumberPeople() == 0) {
            //  更新同一实践下其他申请状态为2
            List<PracticeApplyDO> practiceUpdateList = practiceApplyMapper.selectList("practice_id", practiceDO.getId());
            for (int i=0;i<practiceUpdateList.size();i++){
                if(practiceUpdateList.get(i).getId() == practiceApplyId) continue;
                practiceUpdateList.get(i).setStatus((byte) 2);
            }
            practiceApplyMapper.updateBatch(practiceUpdateList,practiceUpdateList.size());
            //更新当前实践状态为 征集结束3
            practiceDO.setStatus((byte) 3);
            practiceMapper.updateById(practiceDO);
        }



    }

    @Override
    public void reviewFailurePracticeApply(PracticeApplyRejectCreateReqVO rejectCreateReqVO) {
        //验证是否存在
        //validatePracticeApplyExists(rejectCreateReqVO.getApplyId());
        //验证状态是否为待审核
        //validatePracticeApplyStatus(rejectCreateReqVO.getApplyId());

        PracticeApplyDO practiceApplyDO;
        try {
            practiceApplyDO = practiceApplyMapper.selectById(rejectCreateReqVO.getApplyId());

        }catch (Exception e) {
            throw exception(PRACTICE_APPLY_NOT_EXISTS);
        }
        if (practiceApplyDO.getStatus() != 0) {
            throw exception(PRACTICE_APPLY_STATUS_ERROR);
        }
        //修改状态 0 --》 2
       // PracticeApplyDO practiceApplyDO = practiceApplyMapper.selectById(rejectCreateReqVO.getApplyId());
        practiceApplyDO.setStatus((byte) 2);
        practiceApplyMapper.updateById(practiceApplyDO);
        //插入驳回记录
        PracticeApplyRejectDO applyRejectDO = PracticeApplyRejectConvert.INSTANCE.convert(rejectCreateReqVO);
        System.out.println("123"+applyRejectDO);
        rejectMapper.insert(applyRejectDO);

    }
    //
    public PageResult<PracticeApplyDO> getApplyListWithCompanyId(PracticeApplyPageReqVO pageVO,Long companyId){
        List<PracticeDO> practiceList = practiceService.getPassPracticeWithCompanyId(companyId);
        List<Long> practiceIdList = new ArrayList<>();
        for (PracticeDO practice:practiceList) {
            practiceIdList.add(practice.getId());
        }
        pageVO.setPracticeIdList(practiceIdList);

        return practiceApplyMapper.selectPage2(pageVO);
    }

}
