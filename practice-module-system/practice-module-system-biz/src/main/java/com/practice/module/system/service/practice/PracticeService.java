package com.practice.module.system.service.practice;

import java.util.*;
import javax.validation.*;

import com.practice.module.system.controller.admin.practice.vo.practice.*;
import com.practice.module.system.controller.admin.practice.vo.reject.PracticeRejectCreateReqVO;
import com.practice.module.system.dal.dataobject.practice.PracticeDO;
import com.practice.framework.common.pojo.PageResult;

import static com.practice.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.practice.module.system.enums.ErrorCodeConstants.PRACTICE_NOT_EXISTS;

/**
 * 实践 Service 接口
 *
 * @author n3
 */
public interface PracticeService {

    /**
     * 创建实践
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPractice(@Valid PracticeCreateReqVO createReqVO);

    /**
     * 更新实践
     *
     * @param updateReqVO 更新信息
     */
    void updatePracticeApply(@Valid PracticeUpdateReqVO updateReqVO,Long CompanyId);

    /**
     * 删除实践
     *
     * @param id 编号
     */
    void deletePractice(Long id);

    /**
     * 获得实践
     *
     * @param id 编号
     * @return 实践
     */
    PracticeDO getPractice(Long id);

    /**
     * 获得实践列表
     *
     * @param ids 编号
     * @return 实践列表
     */
    List<PracticeDO> getPracticeList(Collection<Long> ids);

    /**
     * 获得实践分页
     *
     * @param pageReqVO 分页查询
     * @return 实践分页
     */
    PageResult<PracticeDO> getPracticePage(PracticePageReqVO pageReqVO);

    /**
     * 获得实践列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 实践列表
     */
    List<PracticeDO> getPracticeList(PracticeExportReqVO exportReqVO);

    void reviewPassPractice(Long id);

    /**
     * 实践申请驳回
     *
     * @param createReqVO 实践申请驳回意见以及文章编号
     * @return
     */
    void reviewFailurePractice(PracticeRejectCreateReqVO createReqVO);

    //验证实践是否存在
    void validatePracticeExists(Long id);
    void validatePracticeStatus(Long id);

    /***
     * 企业端确定学校
     *
     * @Param practiceId 所确定的实践编号
     * */
    void confirmPracticeByCompany(Long practiceId,Long companyId);

    /**
     * 获得实践分页
     *
     * @param pageReqVO 根据实践编号列表分页查询
     * @return 实践分页
     */
    PageResult<PracticeDO> studentGetPracticePage(PracticeIdPageReqVO pageReqVO);

    /**
     * 根据企业id获得通过审核的实践
     *
     * @param companyId 企业编号
     * @return 获取的实践列表
     */
    List<PracticeDO> getPassPracticeWithCompanyId(Long companyId);
}
