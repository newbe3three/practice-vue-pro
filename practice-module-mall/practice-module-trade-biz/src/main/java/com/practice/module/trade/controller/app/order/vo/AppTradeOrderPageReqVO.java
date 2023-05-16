package com.practice.module.trade.controller.app.order.vo;

import com.practice.framework.common.pojo.PageParam;
import com.practice.framework.common.validation.InEnum;
import com.practice.module.trade.enums.order.TradeOrderStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

// TODO 芋艿：字段优化
@Schema(description = "交易订单分页 Request VO")
@Data
public class AppTradeOrderPageReqVO extends PageParam {

    @Schema(description = "订单状态", example = "1")
    @InEnum(value = TradeOrderStatusEnum.class, message = "订单状态必须是 {value}")
    private Integer status;

}
