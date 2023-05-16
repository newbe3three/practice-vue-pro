package com.practice.module.product.convert.brand;

import com.practice.framework.common.pojo.PageResult;
import com.practice.module.product.controller.admin.brand.vo.ProductBrandCreateReqVO;
import com.practice.module.product.controller.admin.brand.vo.ProductBrandRespVO;
import com.practice.module.product.controller.admin.brand.vo.ProductBrandUpdateReqVO;
import com.practice.module.product.dal.dataobject.brand.ProductBrandDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 品牌 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface ProductBrandConvert {

    ProductBrandConvert INSTANCE = Mappers.getMapper(ProductBrandConvert.class);

    ProductBrandDO convert(ProductBrandCreateReqVO bean);

    ProductBrandDO convert(ProductBrandUpdateReqVO bean);

    ProductBrandRespVO convert(ProductBrandDO bean);

    List<ProductBrandRespVO> convertList(List<ProductBrandDO> list);

    PageResult<ProductBrandRespVO> convertPage(PageResult<ProductBrandDO> page);

}
