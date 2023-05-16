package com.practice.module.trade.convert.cart;

import com.practice.module.promotion.api.price.dto.PriceCalculateReqDTO;
import com.practice.module.promotion.api.price.dto.PriceCalculateRespDTO;
import com.practice.module.trade.controller.app.cart.vo.AppTradeCartDetailRespVO;
import com.practice.module.trade.dal.dataobject.cart.TradeCartItemDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;

import static com.practice.framework.common.util.collection.CollectionUtils.convertList;

@Mapper
public interface TradeCartConvert {

    TradeCartConvert INSTANCE = Mappers.getMapper(TradeCartConvert.class);

    default AppTradeCartDetailRespVO buildEmptyAppTradeCartDetailRespVO() {
        return new AppTradeCartDetailRespVO().setItemGroups(Collections.emptyList())
                .setOrder(new AppTradeCartDetailRespVO.Order().setSkuOriginalPrice(0).setSkuPromotionPrice(0)
                        .setOrderPromotionPrice(0).setDeliveryPrice(0).setPayPrice(0));
    }

    default PriceCalculateReqDTO convert(Long userId, List<TradeCartItemDO> cartItems) {
        return new PriceCalculateReqDTO().setUserId(userId)
                .setItems(convertList(cartItems, cartItem -> new PriceCalculateReqDTO.Item().setSkuId(cartItem.getSkuId())
                        .setCount(cartItem.getSelected() ? cartItem.getCount() : 0)));
    }

    // ========== AppTradeCartDetailRespVO 相关 ==========

    AppTradeCartDetailRespVO.Promotion convert(PriceCalculateRespDTO.Promotion bean);

    @Mappings({
            @Mapping(source = "cartItem.count", target = "count")
    })
    AppTradeCartDetailRespVO.Sku convert(PriceCalculateRespDTO.OrderItem orderItem, TradeCartItemDO cartItem);

    AppTradeCartDetailRespVO.Order convert(PriceCalculateRespDTO.Order bean);

}
