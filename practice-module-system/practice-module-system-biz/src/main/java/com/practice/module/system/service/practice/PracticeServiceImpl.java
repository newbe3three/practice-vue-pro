package com.practice.module.system.service.practice;

import com.practice.module.system.controller.admin.practice.vo.practice.PracticeCreateReqVO;
import com.practice.module.system.controller.admin.practice.vo.practice.PracticeExportReqVO;
import com.practice.module.system.controller.admin.practice.vo.practice.PracticePageReqVO;
import com.practice.module.system.controller.admin.practice.vo.practice.PracticeUpdateReqVO;
import com.practice.module.system.controller.admin.practice.vo.reject.PracticeRejectCreateReqVO;
import com.practice.module.system.convert.practice.PracticeRejectConvert;
import com.practice.module.system.dal.mysql.practice.PracticeRejectMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import com.practice.module.system.dal.dataobject.practice.PracticeDO;
import com.practice.framework.common.pojo.PageResult;

import com.practice.module.system.convert.practice.PracticeConvert;
import com.practice.module.system.dal.mysql.practice.PracticeMapper;

import static com.practice.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.practice.module.system.enums.ErrorCodeConstants.*;

/**
 * 实践 Service 实现类
 *
 * @author n3
 */
@Service
@Validated
public class PracticeServiceImpl implements PracticeService {

    @Resource
    private PracticeMapper practiceMapper;
    @Resource
    private PracticeRejectMapper practiceRejectMapper;
    @Override
    public Long createPractice(PracticeCreateReqVO createReqVO) {
        // 插入

        //开始时间在结束时间之前
        int time = createReqVO.getEndTime().compareTo(createReqVO.getStartTime());
        if (time < 0) {
            //结束时间小于开始时间 抛出结束时间小于开始时间异常
            throw exception(PRACTICE_CREATE_TIME_ERROR);
        }
        //需求人数要大于0
        if (createReqVO.getNumberPeople() <= 0) {
            //人数等于0 抛出人数错误异常
            throw exception(PRACTICE_CREATE_PEOPLE_NUMBER_ERROR);
        }
        PracticeDO practice = PracticeConvert.INSTANCE.convert(createReqVO);
        // 默认初始状态为0
        practice.setStatus((byte) 0);
        practiceMapper.insert(practice);
        // 返回
        return practice.getId();
    }

    @Override
    public void updatePracticeApply(PracticeUpdateReqVO updateReqVO) {
        // 校验存在
        validatePracticeExists(updateReqVO.getId());
        //校验状态  是否为已驳回
        if(practiceMapper.selectById(updateReqVO.getId()).getStatus() != 2) {
            throw exception(PRACTICE_UPDATE_STATUS_ERROR);
        }

        // 更新
        PracticeDO updateObj = PracticeConvert.INSTANCE.convert(updateReqVO);
        //修改状态为待审核
        updateObj.setStatus((byte) 0);
        practiceMapper.updateById(updateObj);
    }

    @Override
    public void deletePractice(Long id) {
        // 校验存在
        validatePracticeExists(id);
        // 删除
        practiceMapper.deleteById(id);
    }

    private void validatePracticeExists(Long id) {
        if (practiceMapper.selectById(id) == null) {
            throw exception(PRACTICE_NOT_EXISTS);
        }
    }

    @Override
    public PracticeDO getPractice(Long id) {
        return practiceMapper.selectById(id);
    }

    @Override
    public List<PracticeDO> getPracticeList(Collection<Long> ids) {
        return practiceMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<PracticeDO> getPracticePage(PracticePageReqVO pageReqVO) {
        return practiceMapper.selectPage(pageReqVO);
    }

    @Override
    public List<PracticeDO> getPracticeList(PracticeExportReqVO exportReqVO) {
        return practiceMapper.selectList(exportReqVO);
    }

    @Override
    public void reviewPassPractice(Long id) {
        //验证存在
        validatePracticeExists(id);
        //验证状态是否为待审核 0
        if(practiceMapper.selectById(id).getStatus() != 0) {
            throw exception(PRACTICE_REVIEW_STATUS_ERROR);
        }
        //修改状态为 已通过审核 0 --》 1
        PracticeDO practiceDO = new PracticeDO();
        practiceDO.setStatus((byte) 1);
        practiceDO.setId(id);
        //更新数据
        practiceMapper.updateById(practiceDO);
    }

    @Override
    public void reviewFailurePractice(PracticeRejectCreateReqVO createReqVO) {
        //验证存在
        validatePracticeExists(createReqVO.getPracticeId());
        //验证状态是否为0
        if(practiceMapper.selectById(createReqVO.getPracticeId()).getStatus() != 0) {
            throw exception(PRACTICE_REVIEW_STATUS_ERROR);
        }
        //修改状态为 已驳回 0 --》 2
        PracticeDO practiceDO = new PracticeDO();
        practiceDO.setStatus((byte) 2);
        practiceDO.setId(createReqVO.getPracticeId());
        practiceMapper.updateById(practiceDO);
        //驳回意见插入表中
        practiceRejectMapper.insert(PracticeRejectConvert.INSTANCE.convert(createReqVO));

    }

   


}
