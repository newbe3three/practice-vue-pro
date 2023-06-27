package com.practice.module.system.service.organizationcompany;

import java.util.*;
import javax.validation.*;
import com.practice.module.system.controller.admin.organizationcompany.vo.*;
import com.practice.module.system.dal.dataobject.organizationcompany.OrganizationCompanyDO;
import com.practice.framework.common.pojo.PageResult;

/**
 * 社会企业 Service 接口
 *
 * @author wx
 */
public interface OrganizationCompanyService {

    /**
     * 创建社会企业
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOrganizationCompany(@Valid OrganizationCompanyCreateReqVO createReqVO);

    /**
     * 更新社会企业
     *
     * @param updateReqVO 更新信息
     */
    void updateOrganizationCompany(@Valid OrganizationCompanyUpdateReqVO updateReqVO);

    /**
     * 更新服务约定时间
     *
     * @param updateReqVO 更新信息
     */
    void updateSignOrganizationCompany(OrganizationCompanyUpdateReqVO updateReqVO);


    /**
     * 删除社会企业
     *
     * @param id 编号
     */
    void deleteOrganizationCompany(Long id);

    /**
     * 获得社会企业
     *
     * @param id 编号
     * @return 社会企业
     */
    OrganizationCompanyDO getOrganizationCompany(Long id);

    /**
     * 获得社会企业列表
     *
     * @param ids 编号
     * @return 社会企业列表
     */
    List<OrganizationCompanyDO> getOrganizationCompanyList(Collection<Long> ids);

    /**
     * 获得社会企业分页
     *
     * @param pageReqVO 分页查询
     * @return 社会企业分页
     */
    PageResult<OrganizationCompanyDO> getOrganizationCompanyPage(OrganizationCompanyPageReqVO pageReqVO);

    /**
     * 获得社会企业列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 社会企业列表
     */
    List<OrganizationCompanyDO> getOrganizationCompanyList(OrganizationCompanyExportReqVO exportReqVO);


    void stopServiceOrganizationCompany(Long id);
}
