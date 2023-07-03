package com.practice.module.system.convert.organizationother;

import java.util.*;

import com.practice.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.practice.module.system.controller.admin.organizationother.vo.*;
import com.practice.module.system.dal.dataobject.organizationother.OrganizationOtherDO;

/**
 * 其他组织 Convert
 *
 * @author wx
 */
@Mapper
public interface OrganizationOtherConvert {

    OrganizationOtherConvert INSTANCE = Mappers.getMapper(OrganizationOtherConvert.class);

    OrganizationOtherDO convert(OrganizationOtherCreateReqVO bean);

    OrganizationOtherDO convert(OrganizationOtherUpdateReqVO bean);

    OrganizationOtherRespVO convert(OrganizationOtherDO bean);

    List<OrganizationOtherRespVO> convertList(List<OrganizationOtherDO> list);

    PageResult<OrganizationOtherRespVO> convertPage(PageResult<OrganizationOtherDO> page);

    List<OrganizationOtherExcelVO> convertList02(List<OrganizationOtherDO> list);

}
