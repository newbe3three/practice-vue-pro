package com.practice.module.system.service.taskapply;

import java.util.*;
import javax.validation.*;
import com.practice.module.system.controller.admin.taskapply.vo.*;
import com.practice.module.system.dal.dataobject.taskapply.TaskApplyDO;
import com.practice.framework.common.pojo.PageResult;

/**
 * 任务申请 Service 接口
 *
 * @author n3
 */
public interface TaskApplyService {

    /**
     * 创建任务申请
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTaskApply(@Valid TaskApplyCreateReqVO createReqVO);

    /**
     * 更新任务申请
     *
     * @param updateReqVO 更新信息
     */
    void updateTaskApply(@Valid TaskApplyUpdateReqVO updateReqVO);

    /**
     * 删除任务申请
     *
     * @param id 编号
     */
    void deleteTaskApply(Long id);

    /**
     * 获得任务申请
     *
     * @param id 编号
     * @return 任务申请
     */
    TaskApplyDO getTaskApply(Long id);

    /**
     * 获得任务申请列表
     *
     * @param ids 编号
     * @return 任务申请列表
     */
    List<TaskApplyDO> getTaskApplyList(Collection<Long> ids);

    /**
     * 获得任务申请分页
     *
     * @param pageReqVO 分页查询
     * @return 任务申请分页
     */
    PageResult<TaskApplyDO> getTaskApplyPage(TaskApplyPageReqVO pageReqVO);

    /**
     * 获得任务申请列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 任务申请列表
     */
    List<TaskApplyDO> getTaskApplyList(TaskApplyExportReqVO exportReqVO);
    /**
     * 任务申请审核
     *
     * @param taskApplyReviewReqVO 待审核的任务id和审核结果
     * @return
     */
    void reviewTaskApply(TaskApplyReviewReqVO taskApplyReviewReqVO);

}
