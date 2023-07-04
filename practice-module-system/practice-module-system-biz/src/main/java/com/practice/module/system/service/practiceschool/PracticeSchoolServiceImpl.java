package com.practice.module.system.service.practiceschool;

import com.practice.module.system.dal.dataobject.practice.PracticeDO;
import com.practice.module.system.dal.mysql.dept.DeptMapper;
import com.practice.module.system.dal.mysql.practice.PracticeMapper;
import com.practice.module.system.service.dept.DeptService;
import com.practice.module.system.service.practice.PracticeService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.practice.module.system.controller.admin.practiceschool.vo.*;
import com.practice.module.system.dal.dataobject.practiceschool.PracticeSchoolDO;
import com.practice.framework.common.pojo.PageResult;

import com.practice.module.system.convert.practiceschool.PracticeSchoolConvert;
import com.practice.module.system.dal.mysql.practiceschool.PracticeSchoolMapper;

import static com.practice.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.practice.module.system.enums.ErrorCodeConstants.*;

/**
 * 学校申请实践 Service 实现类
 *
 * @author n3
 */
@Service
@Validated
public class PracticeSchoolServiceImpl implements PracticeSchoolService {

    @Resource
    private PracticeSchoolMapper practiceSchoolMapper;
    @Resource
    private PracticeService practiceService;
    @Resource
    private DeptService deptService;
    @Resource
    private PracticeMapper practiceMapper;


    @Override
    public Long createPracticeSchool(PracticeSchoolCreateReqVO createReqVO) {
        // 插入
        PracticeSchoolDO practiceSchool = PracticeSchoolConvert.INSTANCE.convert(createReqVO);
        practiceSchoolMapper.insert(practiceSchool);
        // 返回
        return practiceSchool.getId();
    }

    @Override
    public void updatePracticeSchool(PracticeSchoolUpdateReqVO updateReqVO) {
        // 校验存在
        validatePracticeSchoolExists(updateReqVO.getId());
        // 更新
        PracticeSchoolDO updateObj = PracticeSchoolConvert.INSTANCE.convert(updateReqVO);
        practiceSchoolMapper.updateById(updateObj);
    }

    @Override
    public void deletePracticeSchool(Long id) {
        // 校验存在
        validatePracticeSchoolExists(id);
        // 删除
        practiceSchoolMapper.deleteById(id);
    }

    private void validatePracticeSchoolExists(Long id) {
        if (practiceSchoolMapper.selectById(id) == null) {
            throw exception(PRACTICE_SCHOOL_NOT_EXISTS);
        }
    }

    @Override
    public PracticeSchoolDO getPracticeSchool(Long id) {
        return practiceSchoolMapper.selectById(id);
    }

    @Override
    public List<PracticeSchoolDO> getPracticeSchoolList(Collection<Long> ids) {
        return practiceSchoolMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<PracticeSchoolDO> getPracticeSchoolPage(PracticeSchoolPageReqVO pageReqVO) {
        return practiceSchoolMapper.selectPage(pageReqVO);
    }

    @Override
    public List<PracticeSchoolDO> getPracticeSchoolList(PracticeSchoolExportReqVO exportReqVO) {
        return practiceSchoolMapper.selectList(exportReqVO);
    }



    @Override
    public Long applyPracticeSchool(PracticeSchoolCreateReqVO createReqVO) {
        //验证实践是否存在
        practiceService.validatePracticeExists(createReqVO.getPracticeId());
        //验证实践状态
        practiceService.validatePracticeStatus(createReqVO.getPracticeId());
        //验证学校id是否存在
        deptService.validateDeptExists(createReqVO.getSchoolId());

        PracticeSchoolDO practiceSchool = PracticeSchoolConvert.INSTANCE.convert(createReqVO);
        practiceSchool.setStatus((byte) 0);
        practiceSchoolMapper.insert(practiceSchool);

        return practiceSchool.getId();
    }

    @Override
    public void reviewPassPracticeApply(Long practiceSchoolId) {
        //1.验证存在性
        validatePracticeSchoolExists(practiceSchoolId);
        //通过申请，修改该学校申请的状态为通过，同时修改同一实践其他申请状态为不通过
        PracticeSchoolDO practiceSchoolPass = practiceSchoolMapper.selectById(practiceSchoolId);
        // 2.验证当前申请是否已通过审核
        if(practiceSchoolPass.getStatus() != 0) {
            throw exception(PRACTICE_SCHOOL_STATUS_ERROR);
        }
        Long practiceId = practiceSchoolPass.getPracticeId();
        // 3..修改当前申请状态为 1
        practiceSchoolPass.setStatus((byte) 1);
        practiceSchoolMapper.updateById(practiceSchoolPass);
        //4..根据实践编号查询需要修改为不通过的列表
        List<PracticeSchoolDO> practiceDOList = practiceSchoolMapper.selectList("practice_id", practiceId);
        //修改这些数据的状态
        for (int i=0;i<practiceDOList.size();i++) {
            if (practiceDOList.get(i).getStatus() == 1) {
                continue;
            }

            practiceDOList.get(i).setStatus((byte) 2);
            //逐个更新
            //practiceSchoolMapper.updateBatch(practiceDOList.get(i));
        }
        // 批量更新
        practiceSchoolMapper.updateBatch(practiceDOList,practiceDOList.size());
        //5.. 修改practice的状态为征集中  2
        //practiceService.validatePracticeExists(practiceId);
        PracticeDO practiceDO = practiceMapper.selectById(practiceId);
        practiceDO.setStatus((byte) 2);
        practiceMapper.updateById(practiceDO);
    }

}
