package com.practice.module.system.service.worklog;

import java.util.*;
import javax.validation.*;
import com.practice.module.system.controller.admin.worklog.vo.*;
import com.practice.module.system.dal.dataobject.worklog.WorkLogDO;
import com.practice.framework.common.pojo.PageResult;

/**
 * 工作日志 Service 接口
 *
 * @author 觅践
 */
public interface WorkLogService {

    /**
     * 创建工作日志
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createWorkLog(@Valid WorkLogCreateReqVO createReqVO);

    /**
     * 更新工作日志
     *
     * @param updateReqVO 更新信息
     */
    void updateWorkLog(@Valid WorkLogUpdateReqVO updateReqVO);

    /**
     * 删除工作日志
     *
     * @param id 编号
     */
    void deleteWorkLog(Long id);

    /**
     * 获得工作日志
     *
     * @param id 编号
     * @return 工作日志
     */
    WorkLogDO getWorkLog(Long id);

    /**
     * 获得工作日志列表
     *
     * @param ids 编号
     * @return 工作日志列表
     */
    List<WorkLogDO> getWorkLogList(Collection<Long> ids);

    /**
     * 获得工作日志分页
     *
     * @param pageReqVO 分页查询
     * @return 工作日志分页
     */
    PageResult<WorkLogDO> getWorkLogPage(WorkLogPageReqVO pageReqVO);

    /**
     * 获得工作日志列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 工作日志列表
     */
    List<WorkLogDO> getWorkLogList(WorkLogExportReqVO exportReqVO);

}
