package com.practice.module.system.service.logger;

import com.practice.framework.common.pojo.PageResult;
import com.practice.module.system.api.logger.dto.OperateLogCreateReqDTO;
import com.practice.module.system.controller.admin.logger.vo.operatelog.OperateLogExportReqVO;
import com.practice.module.system.controller.admin.logger.vo.operatelog.OperateLogPageReqVO;
import com.practice.module.system.dal.dataobject.logger.OperateLogDO;

import java.util.List;

/**
 * 操作日志 Service 接口
 *
 * @author 芋道源码
 */
public interface OperateLogService {

    /**
     * 记录操作日志
     *
     * @param createReqDTO 操作日志请求
     */
    void createOperateLog(OperateLogCreateReqDTO createReqDTO);

    /**
     * 获得操作日志分页列表
     *
     * @param reqVO 分页条件
     * @return 操作日志分页列表
     */
    PageResult<OperateLogDO> getOperateLogPage(OperateLogPageReqVO reqVO);

    /**
     * 获得操作日志列表
     *
     * @param reqVO 列表条件
     * @return 日志列表
     */
    List<OperateLogDO> getOperateLogList(OperateLogExportReqVO reqVO);

}
