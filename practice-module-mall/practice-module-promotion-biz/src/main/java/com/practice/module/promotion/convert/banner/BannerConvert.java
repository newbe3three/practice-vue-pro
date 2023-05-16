package com.practice.module.promotion.convert.banner;

import com.practice.framework.common.pojo.PageResult;
import com.practice.module.promotion.controller.admin.banner.vo.BannerCreateReqVO;
import com.practice.module.promotion.controller.admin.banner.vo.BannerRespVO;
import com.practice.module.promotion.controller.admin.banner.vo.BannerUpdateReqVO;
import com.practice.module.promotion.dal.dataobject.banner.BannerDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BannerConvert {

    BannerConvert INSTANCE = Mappers.getMapper(BannerConvert.class);

    List<BannerRespVO> convertList(List<BannerDO> list);

    PageResult<BannerRespVO> convertPage(PageResult<BannerDO> pageResult);

    BannerRespVO convert(BannerDO banner);

    BannerDO convert(BannerCreateReqVO createReqVO);

    BannerDO convert(BannerUpdateReqVO updateReqVO);

}
