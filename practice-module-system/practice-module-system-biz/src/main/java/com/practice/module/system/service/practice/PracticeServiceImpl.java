package com.practice.module.system.service.practice;

import com.practice.module.system.controller.admin.practice.vo.practice.*;
import com.practice.module.system.controller.admin.practice.vo.reject.PracticeRejectCreateReqVO;
import com.practice.module.system.convert.practice.PracticeRejectConvert;
import com.practice.module.system.dal.dataobject.practiceschool.PracticeSchoolDO;
import com.practice.module.system.dal.mysql.practice.PracticeRejectMapper;
import com.practice.module.system.dal.mysql.practiceschool.PracticeSchoolMapper;
import com.practice.module.system.dal.mysql.user.AdminUserMapper;
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
    @Resource
    private AdminUserMapper adminUserMapper;
    @Resource
    private PracticeSchoolMapper practiceSchoolMapper;
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

        PracticeDO practice = PracticeConvert.INSTANCE.convert2(createReqVO);
        // 默认初始状态为0
        practice.setStatus((byte) 0);
        practiceMapper.insert(practice);
        // 返回
        return practice.getId();
    }

    @Override
    public void updatePracticeApply(PracticeUpdateReqVO updateReqVO, Long companyId) {
        // 校验存在
        validatePracticeExists(updateReqVO.getId());
        // 校验归属

        validatePracticeCompanyId(updateReqVO.getId(),companyId);
//        if(practiceMapper.selectById(updateReqVO.getId()).getCompanyId() != companyId) {
//            throw exception(PRACTICE_PERMISSION_ERROR);
//        }

        //开始时间在结束时间之前
        int time = updateReqVO.getEndTime().compareTo(updateReqVO.getStartTime());
        if (time < 0) {
            //结束时间小于开始时间 抛出结束时间小于开始时间异常
            throw exception(PRACTICE_CREATE_TIME_ERROR);
        }

        //todo: 实践审核通过或者在征集中都可以更新实践信息
        //校验状态 在实践状态为已驳回或待审核都可以更更新实践信息
        Byte status = practiceMapper.selectById(updateReqVO.getId()).getStatus();
        if(status == 2 || status == 0) {
            // 更新
            PracticeDO updateObj = PracticeConvert.INSTANCE.convert(updateReqVO);
            //修改状态为待审核
            updateObj.setStatus((byte) 0);
            practiceMapper.updateById(updateObj);
        } else {
            throw exception(PRACTICE_UPDATE_STATUS_ERROR);
        }


    }

    @Override
    public void deletePractice(Long id) {
        // 校验存在
        validatePracticeExists(id);
        // 删除
        practiceMapper.deleteById(id);
    }

    public void validatePracticeExists(Long id) {
        if (practiceMapper.selectById(id) == null) {
            throw exception(PRACTICE_NOT_EXISTS);
        }
    }

    public void validatePracticeCompanyId(Long practiceId,Long companyId){
        if(practiceMapper.selectById(practiceId).getCompanyId() != companyId) {
            throw exception(PRACTICE_PERMISSION_ERROR);
        }
    }

    public void validatePracticeStatus(Long id) {
        //验证实践状态是否为 审核通过 1
        if(practiceMapper.selectById(id).getStatus() != 1) {
            throw exception(PRACTICE_STATUS_ERROR);
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

    // 企业端可以在选定学校后，修改状态，也可以不选择任何院校，让实践面向所有学生
    public void confirmPracticeByCompany(Long practiceId,Long companyId){
        //校验存在性
        validatePracticeExists(practiceId);
        //校验归属
        validatePracticeCompanyId(practiceId,companyId);
        //校验实践状态为审核通过 status=1
        validatePracticeStatus(practiceId);

        //根据实践编号，修改实践状态为 3 （征集中，面向指定院校学生，或全部学生）
        PracticeDO practiceDO = new PracticeDO();
        practiceDO.setId(practiceId);
        practiceDO.setStatus((byte) 3);
        practiceMapper.updateById(practiceDO);

        //修改对这个实践的其他未通过的申请状态为已驳回。状态为 2
        boolean flag = false;
        List<PracticeSchoolDO> practiceSchoolApplyList = practiceSchoolMapper.selectList("practice_id", practiceId);
        for (int i=0;i<practiceSchoolApplyList.size();i++) {
            //已通过审核的院校申请则不能修改状态 保持原状
            if (practiceSchoolApplyList.get(i).getStatus() == 1) {
                //如果存在通过审核的院校申请，则设置flag值，标志面向指定院校
                flag = true;
                continue;
            }
            practiceSchoolApplyList.get(i).setStatus((byte) 2);
        }
        if (flag) {
            practiceSchoolMapper.updateBatch(practiceSchoolApplyList,practiceSchoolApplyList.size());
        }
        // flag == false 就标志面向全部院校，此时需要向practice_school中插入一条schoolId为0的数据项，代表面向全部学生
        if(!flag) {
            PracticeSchoolDO practiceSchoolDO = new PracticeSchoolDO();
            practiceSchoolDO.setPracticeId(practiceId);
            practiceSchoolDO.setSchoolId(0L);
            practiceSchoolDO.setStatus((byte) 1);
            practiceSchoolMapper.insert(practiceSchoolDO);
        }

    }

    public List<PracticeDO> getPassPracticeWithCompanyId(Long companyId) {
        List<Byte> statusList = new ArrayList<>();
        statusList.add((byte)1);
        statusList.add((byte)4);
        statusList.add((byte)3);
        return  practiceMapper.selectList(companyId,statusList);
    }

}
