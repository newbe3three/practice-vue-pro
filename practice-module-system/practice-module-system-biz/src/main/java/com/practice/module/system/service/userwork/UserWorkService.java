package com.practice.module.system.service.userwork;

import java.util.*;
import javax.validation.*;
import com.practice.module.system.controller.admin.userwork.vo.*;
import com.practice.module.system.dal.dataobject.userwork.UserWorkDO;
import com.practice.framework.common.pojo.PageResult;

/**
 * 工作经历 Service 接口
 *
 * @author n3
 */
public interface UserWorkService {

    /**
     * 创建工作经历
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createUserWork(@Valid UserWorkCreateReqVO createReqVO);

    /**
     * 更新工作经历
     *
     * @param updateReqVO 更新信息
     */
    void updateUserWork(@Valid UserWorkUpdateReqVO updateReqVO);

    /**
     * 删除工作经历
     *
     * @param id 编号
     */
    void deleteUserWork(Long id,Long userId);

    /**
     * 获得工作经历
     *
     * @param id 编号
     * @return 工作经历
     */
    UserWorkDO getUserWork(Long id);

    /**
     * 获得工作经历列表
     *
     * @param ids 编号
     * @return 工作经历列表
     */
    List<UserWorkDO> getUserWorkList(Collection<Long> ids);

    /**
     * 获得工作经历分页
     *
     * @param pageReqVO 分页查询
     * @return 工作经历分页
     */
    PageResult<UserWorkDO> getUserWorkPage(UserWorkPageReqVO pageReqVO);

    /**
     * 获得工作经历列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 工作经历列表
     */
    List<UserWorkDO> getUserWorkList(UserWorkExportReqVO exportReqVO);

    List<UserWorkDO> getUserWorkWithUserId(Long userId);


}
