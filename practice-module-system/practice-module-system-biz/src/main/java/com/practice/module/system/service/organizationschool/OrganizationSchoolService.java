package com.practice.module.system.service.organizationschool;

import java.util.*;
import javax.validation.*;
import com.practice.module.system.controller.admin.organizationschool.vo.*;
import com.practice.module.system.dal.dataobject.organizationschool.OrganizationSchoolDO;
import com.practice.framework.common.pojo.PageResult;

/**
 * 教育院校 Service 接口
 *
 * @author wx
 */
public interface OrganizationSchoolService {

    /**
     * 创建教育院校
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOrganizationSchool(@Valid OrganizationSchoolCreateReqVO createReqVO);

    /**
     * 更新教育院校
     *
     * @param updateReqVO 更新信息
     */
    void updateOrganizationSchool(@Valid OrganizationSchoolUpdateReqVO updateReqVO);

    /**
     * 删除教育院校
     *
     * @param id 编号
     */
    void deleteOrganizationSchool(Long id);

    /**
     * 获得教育院校
     *
     * @param id 编号
     * @return 教育院校
     */
    OrganizationSchoolDO getOrganizationSchool(Long id);

    /**
     * 获得教育院校列表
     *
     * @param ids 编号
     * @return 教育院校列表
     */
    List<OrganizationSchoolDO> getOrganizationSchoolList(Collection<Long> ids);

    /**
     * 获得教育院校分页
     *
     * @param pageReqVO 分页查询
     * @return 教育院校分页
     */
    PageResult<OrganizationSchoolDO> getOrganizationSchoolPage(OrganizationSchoolPageReqVO pageReqVO);

    /**
     * 获得教育院校列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 教育院校列表
     */
    List<OrganizationSchoolDO> getOrganizationSchoolList(OrganizationSchoolExportReqVO exportReqVO);

    void stopServiceOrganizationSchool(Long id);

    void updateSignOrganizationSchool(OrganizationSchoolUpdateReqVO updateReqVO);
}
