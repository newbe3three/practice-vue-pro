package com.practice.module.system.dal.mysql.organizationschool;

import java.util.*;

import com.practice.framework.common.pojo.PageResult;
import com.practice.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.practice.framework.mybatis.core.mapper.BaseMapperX;
import com.practice.module.system.dal.dataobject.organizationschool.OrganizationSchoolDO;
import org.apache.ibatis.annotations.Mapper;
import com.practice.module.system.controller.admin.organizationschool.vo.*;

/**
 * 教育院校 Mapper
 *
 * @author wx
 */
@Mapper
public interface OrganizationSchoolMapper extends BaseMapperX<OrganizationSchoolDO> {

    default PageResult<OrganizationSchoolDO> selectPage(OrganizationSchoolPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OrganizationSchoolDO>()
                .eqIfPresent(OrganizationSchoolDO::getId, reqVO.getId())
                .likeIfPresent(OrganizationSchoolDO::getName, reqVO.getName())
                .eqIfPresent(OrganizationSchoolDO::getAbbreviation, reqVO.getAbbreviation())
                .betweenIfPresent(OrganizationSchoolDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(OrganizationSchoolDO::getId));
    }

    default List<OrganizationSchoolDO> selectList(OrganizationSchoolExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<OrganizationSchoolDO>()
                .eqIfPresent(OrganizationSchoolDO::getId, reqVO.getId())
                .likeIfPresent(OrganizationSchoolDO::getName, reqVO.getName())
                .eqIfPresent(OrganizationSchoolDO::getAbbreviation, reqVO.getAbbreviation())
                .betweenIfPresent(OrganizationSchoolDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(OrganizationSchoolDO::getId));
    }

}
