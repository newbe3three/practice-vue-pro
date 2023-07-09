package com.practice.module.system.service.practiceapply;

import java.util.*;
import javax.validation.*;

import com.practice.module.system.controller.admin.practice.vo.reject.PracticeRejectCreateReqVO;
import com.practice.module.system.controller.admin.practiceapply.vo.apply.PracticeApplyCreateReqVO;
import com.practice.module.system.controller.admin.practiceapply.vo.apply.PracticeApplyExportReqVO;
import com.practice.module.system.controller.admin.practiceapply.vo.apply.PracticeApplyPageReqVO;
import com.practice.module.system.controller.admin.practiceapply.vo.apply.PracticeApplyUpdateReqVO;
import com.practice.module.system.controller.admin.practiceapply.vo.reject.PracticeApplyRejectCreateReqVO;
import com.practice.module.system.dal.dataobject.practice.PracticeRejectDO;
import com.practice.module.system.dal.dataobject.practiceapply.PracticeApplyDO;
import com.practice.framework.common.pojo.PageResult;
import com.practice.module.system.dal.dataobject.practiceapply.PracticeApplyRejectDO;

/**
 * 实践申请 Service 接口
 *
 * @author n3
 */
public interface PracticeApplyService {

    /**
     * 创建实践申请
     *
     * @param PracticeApplyDO 创建信息
     * @return 编号
     */
    Long createPracticeApply(@Valid PracticeApplyDO PracticeApplyDO);

    /**
     * 更新实践申请
     *
     * @param PracticeApplyDO 更新信息
     */
    void updatePracticeApply(@Valid PracticeApplyDO PracticeApplyDO);

    /**
     * 删除实践申请
     *
     * @param id 编号
     */
    void deletePracticeApply(Long id);

    /**
     * 获得实践申请
     *
     * @param id 编号
     * @return 实践申请
     */
    PracticeApplyDO getPracticeApply(Long id);

    /**
     * 获得实践申请列表
     *
     * @param ids 编号
     * @return 实践申请列表
     */
    List<PracticeApplyDO> getPracticeApplyList(Collection<Long> ids);

    /**
     * 获得实践申请分页
     *
     * @param pageReqVO 分页查询
     * @return 实践申请分页
     */
    PageResult<PracticeApplyDO> getPracticeApplyPage(PracticeApplyPageReqVO pageReqVO);

    /**
     * 获得实践申请列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 实践申请列表
     */
    List<PracticeApplyDO> getPracticeApplyList(PracticeApplyExportReqVO exportReqVO);

    /**
     * 获得对应id申请实践的驳回列表
     *
     * @param practiceApplyId 申请实践id
     * @return 实践申请驳回记录列表
     */
    List<PracticeApplyRejectDO> getPracticeApplyRejectListByPracticeApplyId(Long practiceApplyId);
    /**
     * 根据id通过申请
     *
     * @param practiceApplyId 申请实践id
     * @return
     */
    void reviewPassPracticeApply(Long practiceApplyId);
    /**
     * 驳回申请，并添加驳回记录
     *
     * @param rejectCreateReqVO 驳回记录
     * @return
     */
    void reviewFailurePracticeApply(PracticeApplyRejectCreateReqVO rejectCreateReqVO);

    /**
     * 根据企业编号查询申请列表
     *
     * @param companyId 企业编号
     * @return 申请列表
     */
    PageResult<PracticeApplyDO> getApplyListWithCompanyId(PracticeApplyPageReqVO pageVO,Long companyId);

}
