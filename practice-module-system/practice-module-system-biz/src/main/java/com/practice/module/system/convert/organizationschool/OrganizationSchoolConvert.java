package com.practice.module.system.convert.organizationschool;

import java.util.*;

import com.practice.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.practice.module.system.controller.admin.organizationschool.vo.*;
import com.practice.module.system.dal.dataobject.organizationschool.OrganizationSchoolDO;

/**
 * 教育院校 Convert
 *
 * @author wx
 */
@Mapper
public interface OrganizationSchoolConvert {

    OrganizationSchoolConvert INSTANCE = Mappers.getMapper(OrganizationSchoolConvert.class);

    OrganizationSchoolDO convert(OrganizationSchoolCreateReqVO bean);

    OrganizationSchoolDO convert(OrganizationSchoolUpdateReqVO bean);

    OrganizationSchoolRespVO convert(OrganizationSchoolDO bean);

    List<OrganizationSchoolRespVO> convertList(List<OrganizationSchoolDO> list);

    PageResult<OrganizationSchoolRespVO> convertPage(PageResult<OrganizationSchoolDO> page);

    List<OrganizationSchoolExcelVO> convertList02(List<OrganizationSchoolDO> list);

}
