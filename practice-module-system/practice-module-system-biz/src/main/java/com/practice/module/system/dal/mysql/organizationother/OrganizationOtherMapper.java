package com.practice.module.system.dal.mysql.organizationother;

import java.util.*;

import com.practice.framework.common.pojo.PageResult;
import com.practice.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.practice.framework.mybatis.core.mapper.BaseMapperX;
import com.practice.module.system.dal.dataobject.organizationother.OrganizationOtherDO;
import org.apache.ibatis.annotations.Mapper;
import com.practice.module.system.controller.admin.organizationother.vo.*;

/**
 * 其他组织 Mapper
 *
 * @author wx
 */
@Mapper
public interface OrganizationOtherMapper extends BaseMapperX<OrganizationOtherDO> {

    default PageResult<OrganizationOtherDO> selectPage(OrganizationOtherPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OrganizationOtherDO>()
                .eqIfPresent(OrganizationOtherDO::getId, reqVO.getId())
                .likeIfPresent(OrganizationOtherDO::getName, reqVO.getName())
                .eqIfPresent(OrganizationOtherDO::getAbbreviation, reqVO.getAbbreviation())
                .betweenIfPresent(OrganizationOtherDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(OrganizationOtherDO::getId));
    }

    default List<OrganizationOtherDO> selectList(OrganizationOtherExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<OrganizationOtherDO>()
                .eqIfPresent(OrganizationOtherDO::getId, reqVO.getId())
                .likeIfPresent(OrganizationOtherDO::getName, reqVO.getName())
                .eqIfPresent(OrganizationOtherDO::getAbbreviation, reqVO.getAbbreviation())
                .betweenIfPresent(OrganizationOtherDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(OrganizationOtherDO::getId));
    }

}
