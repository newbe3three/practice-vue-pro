package com.practice.module.pay.convert.demo;

import com.practice.framework.common.pojo.PageResult;
import com.practice.module.pay.controller.admin.demo.vo.PayDemoOrderCreateReqVO;
import com.practice.module.pay.controller.admin.demo.vo.PayDemoOrderRespVO;
import com.practice.module.pay.dal.dataobject.demo.PayDemoOrderDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 示例订单 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface PayDemoOrderConvert {

    PayDemoOrderConvert INSTANCE = Mappers.getMapper(PayDemoOrderConvert.class);

    PayDemoOrderDO convert(PayDemoOrderCreateReqVO bean);

    PayDemoOrderRespVO convert(PayDemoOrderDO bean);

    PageResult<PayDemoOrderRespVO> convertPage(PageResult<PayDemoOrderDO> page);

}
