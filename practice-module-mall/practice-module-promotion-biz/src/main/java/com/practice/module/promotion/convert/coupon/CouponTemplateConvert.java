package com.practice.module.promotion.convert.coupon;

import com.practice.framework.common.pojo.PageResult;
import com.practice.module.promotion.controller.admin.coupon.vo.template.CouponTemplateCreateReqVO;
import com.practice.module.promotion.controller.admin.coupon.vo.template.CouponTemplateRespVO;
import com.practice.module.promotion.controller.admin.coupon.vo.template.CouponTemplateUpdateReqVO;
import com.practice.module.promotion.dal.dataobject.coupon.CouponTemplateDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 优惠劵模板 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface CouponTemplateConvert {

    CouponTemplateConvert INSTANCE = Mappers.getMapper(CouponTemplateConvert.class);

    CouponTemplateDO convert(CouponTemplateCreateReqVO bean);

    CouponTemplateDO convert(CouponTemplateUpdateReqVO bean);

    CouponTemplateRespVO convert(CouponTemplateDO bean);

    PageResult<CouponTemplateRespVO> convertPage(PageResult<CouponTemplateDO> page);

}
