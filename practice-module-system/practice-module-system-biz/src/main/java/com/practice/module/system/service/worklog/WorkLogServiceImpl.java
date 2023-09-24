package com.practice.module.system.service.worklog;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.practice.module.system.controller.admin.worklog.vo.*;
import com.practice.module.system.dal.dataobject.worklog.WorkLogDO;
import com.practice.framework.common.pojo.PageResult;

import com.practice.module.system.convert.worklog.WorkLogConvert;
import com.practice.module.system.dal.mysql.worklog.WorkLogMapper;

import static com.practice.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.practice.module.system.enums.ErrorCodeConstants.*;

/**
 * 工作日志 Service 实现类
 *
 * @author 觅践
 */
@Service
@Validated
public class WorkLogServiceImpl implements WorkLogService {

    @Resource
    private WorkLogMapper workLogMapper;

    @Override
    public Long createWorkLog(WorkLogCreateReqVO createReqVO) {
        // 插入
        WorkLogDO workLog = WorkLogConvert.INSTANCE.convert(createReqVO);
        workLogMapper.insert(workLog);
        // 返回
        return workLog.getId();
    }

    @Override
    public void updateWorkLog(WorkLogUpdateReqVO updateReqVO) {
        // 校验存在
        validateWorkLogExists(updateReqVO.getId());
        // 更新
        WorkLogDO updateObj = WorkLogConvert.INSTANCE.convert(updateReqVO);
        workLogMapper.updateById(updateObj);
    }

    @Override
    public void deleteWorkLog(Long id) {
        // 校验存在
        validateWorkLogExists(id);
        // 删除
        workLogMapper.deleteById(id);
    }

    private void validateWorkLogExists(Long id) {
        if (workLogMapper.selectById(id) == null) {
            throw exception(WORK_LOG_NOT_EXISTS);
        }
    }

    @Override
    public WorkLogDO getWorkLog(Long id) {
        return workLogMapper.selectById(id);
    }

    @Override
    public List<WorkLogDO> getWorkLogList(Collection<Long> ids) {
        return workLogMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<WorkLogDO> getWorkLogPage(WorkLogPageReqVO pageReqVO) {
        return workLogMapper.selectPage(pageReqVO);
    }

    @Override
    public List<WorkLogDO> getWorkLogList(WorkLogExportReqVO exportReqVO) {
        return workLogMapper.selectList(exportReqVO);
    }

}
