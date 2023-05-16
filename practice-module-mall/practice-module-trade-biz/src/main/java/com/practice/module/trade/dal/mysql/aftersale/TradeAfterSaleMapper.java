package com.practice.module.trade.dal.mysql.aftersale;

import com.practice.framework.common.pojo.PageResult;
import com.practice.framework.mybatis.core.mapper.BaseMapperX;
import com.practice.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.practice.module.trade.controller.admin.aftersale.vo.TradeAfterSalePageReqVO;
import com.practice.module.trade.dal.dataobject.aftersale.TradeAfterSaleDO;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TradeAfterSaleMapper extends BaseMapperX<TradeAfterSaleDO> {

    default PageResult<TradeAfterSaleDO> selectPage(TradeAfterSalePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TradeAfterSaleDO>()
                .likeIfPresent(TradeAfterSaleDO::getNo, reqVO.getNo())
                .eqIfPresent(TradeAfterSaleDO::getStatus, reqVO.getStatus())
                .eqIfPresent(TradeAfterSaleDO::getType, reqVO.getType())
                .eqIfPresent(TradeAfterSaleDO::getWay, reqVO.getWay())
                .likeIfPresent(TradeAfterSaleDO::getOrderNo, reqVO.getOrderNo())
                .likeIfPresent(TradeAfterSaleDO::getSpuName, reqVO.getSpuName())
                .betweenIfPresent(TradeAfterSaleDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TradeAfterSaleDO::getId));
    }

    default int updateByIdAndStatus(Long id, Integer status, TradeAfterSaleDO update) {
        return update(update, new LambdaUpdateWrapper<TradeAfterSaleDO>()
                .eq(TradeAfterSaleDO::getId, id).eq(TradeAfterSaleDO::getStatus, status));
    }

    default TradeAfterSaleDO selectByPayRefundId(Long payRefundId) {
        return selectOne(TradeAfterSaleDO::getPayRefundId, payRefundId);
    }

}
