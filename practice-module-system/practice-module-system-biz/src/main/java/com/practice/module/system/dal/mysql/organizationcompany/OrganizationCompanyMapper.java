package com.practice.module.system.dal.mysql.organizationcompany;

import java.util.*;

import com.practice.framework.common.pojo.PageResult;
import com.practice.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.practice.framework.mybatis.core.mapper.BaseMapperX;
import com.practice.module.system.dal.dataobject.organizationcompany.OrganizationCompanyDO;
import org.apache.ibatis.annotations.Mapper;
import com.practice.module.system.controller.admin.organizationcompany.vo.*;

/**
 * 社会企业 Mapper
 *
 * @author wx
 */
@Mapper
public interface OrganizationCompanyMapper extends BaseMapperX<OrganizationCompanyDO> {

    default PageResult<OrganizationCompanyDO> selectPage(OrganizationCompanyPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OrganizationCompanyDO>()
                .eqIfPresent(OrganizationCompanyDO::getId, reqVO.getId())
                .likeIfPresent(OrganizationCompanyDO::getName, reqVO.getName())
                .likeIfPresent(OrganizationCompanyDO::getAbbreviation, reqVO.getAbbreviation())
                .betweenIfPresent(OrganizationCompanyDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(OrganizationCompanyDO::getId));
    }

    default List<OrganizationCompanyDO> selectList(OrganizationCompanyExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<OrganizationCompanyDO>()
                .eqIfPresent(OrganizationCompanyDO::getId, reqVO.getId())
                .likeIfPresent(OrganizationCompanyDO::getName, reqVO.getName())
                .likeIfPresent(OrganizationCompanyDO::getAbbreviation, reqVO.getAbbreviation())
                .betweenIfPresent(OrganizationCompanyDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(OrganizationCompanyDO::getId));
    }

}
