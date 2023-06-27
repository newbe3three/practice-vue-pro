package com.practice.module.system.service.organizationother;

import java.util.*;
import javax.validation.*;
import com.practice.module.system.controller.admin.organizationother.vo.*;
import com.practice.module.system.dal.dataobject.organizationother.OrganizationOtherDO;
import com.practice.framework.common.pojo.PageResult;

/**
 * 其他组织 Service 接口
 *
 * @author wx
 */
public interface OrganizationOtherService {

    /**
     * 创建其他组织
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOrganizationOther(@Valid OrganizationOtherCreateReqVO createReqVO);

    /**
     * 更新其他组织
     *
     * @param updateReqVO 更新信息
     */
    void updateOrganizationOther(@Valid OrganizationOtherUpdateReqVO updateReqVO);

    /**
     * 删除其他组织
     *
     * @param id 编号
     */
    void deleteOrganizationOther(Long id);

    /**
     * 获得其他组织
     *
     * @param id 编号
     * @return 其他组织
     */
    OrganizationOtherDO getOrganizationOther(Long id);

    /**
     * 获得其他组织列表
     *
     * @param ids 编号
     * @return 其他组织列表
     */
    List<OrganizationOtherDO> getOrganizationOtherList(Collection<Long> ids);

    /**
     * 获得其他组织分页
     *
     * @param pageReqVO 分页查询
     * @return 其他组织分页
     */
    PageResult<OrganizationOtherDO> getOrganizationOtherPage(OrganizationOtherPageReqVO pageReqVO);

    /**
     * 获得其他组织列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 其他组织列表
     */
    List<OrganizationOtherDO> getOrganizationOtherList(OrganizationOtherExportReqVO exportReqVO);

    void stopServiceOrganizationOther(Long id);
}
