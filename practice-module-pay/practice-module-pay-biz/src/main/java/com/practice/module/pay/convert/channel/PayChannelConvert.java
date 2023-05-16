package com.practice.module.pay.convert.channel;

import java.util.*;

import com.practice.framework.common.pojo.PageResult;

import com.practice.module.pay.controller.admin.merchant.vo.channel.PayChannelCreateReqVO;
import com.practice.module.pay.controller.admin.merchant.vo.channel.PayChannelExcelVO;
import com.practice.module.pay.controller.admin.merchant.vo.channel.PayChannelRespVO;
import com.practice.module.pay.controller.admin.merchant.vo.channel.PayChannelUpdateReqVO;
import com.practice.module.pay.dal.dataobject.merchant.PayChannelDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PayChannelConvert {

    PayChannelConvert INSTANCE = Mappers.getMapper(PayChannelConvert.class);

    @Mapping(target = "config",ignore = true)
    PayChannelDO convert(PayChannelCreateReqVO bean);

    @Mapping(target = "config",ignore = true)
    PayChannelDO convert(PayChannelUpdateReqVO bean);

    @Mapping(target = "config",expression = "java(com.practice.framework.common.util.json.JsonUtils.toJsonString(bean.getConfig()))")
    PayChannelRespVO convert(PayChannelDO bean);

    List<PayChannelRespVO> convertList(List<PayChannelDO> list);

    PageResult<PayChannelRespVO> convertPage(PageResult<PayChannelDO> page);

    List<PayChannelExcelVO> convertList02(List<PayChannelDO> list);



}
