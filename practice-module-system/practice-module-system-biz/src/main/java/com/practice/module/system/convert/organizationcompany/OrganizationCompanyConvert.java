package com.practice.module.system.convert.organizationcompany;

import java.util.*;

import com.practice.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.practice.module.system.controller.admin.organizationcompany.vo.*;
import com.practice.module.system.dal.dataobject.organizationcompany.OrganizationCompanyDO;

/**
 * 社会企业 Convert
 *
 * @author wx
 */
@Mapper
public interface OrganizationCompanyConvert {

    OrganizationCompanyConvert INSTANCE = Mappers.getMapper(OrganizationCompanyConvert.class);

    OrganizationCompanyDO convert(OrganizationCompanyCreateReqVO bean);

    OrganizationCompanyDO convert(OrganizationCompanyUpdateReqVO bean);

    OrganizationCompanyRespVO convert(OrganizationCompanyDO bean);

    List<OrganizationCompanyRespVO> convertList(List<OrganizationCompanyDO> list);

    PageResult<OrganizationCompanyRespVO> convertPage(PageResult<OrganizationCompanyDO> page);

    List<OrganizationCompanyExcelVO> convertList02(List<OrganizationCompanyDO> list);


}
