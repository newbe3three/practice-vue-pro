package com.practice.module.promotion.api.price;

import com.practice.module.promotion.api.price.dto.PriceCalculateReqDTO;
import com.practice.module.promotion.api.price.dto.PriceCalculateRespDTO;
import com.practice.module.promotion.service.price.PriceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 价格 API 实现类
 *
 * @author 芋道源码
 */
@Service
public class PriceApiImpl implements PriceApi {

    @Resource
    private PriceService priceService;

    @Override
    public PriceCalculateRespDTO calculatePrice(PriceCalculateReqDTO calculateReqDTO) {
        return priceService.calculatePrice(calculateReqDTO);
    }

}
