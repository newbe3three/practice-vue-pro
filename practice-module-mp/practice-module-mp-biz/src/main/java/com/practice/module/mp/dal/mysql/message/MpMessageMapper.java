package com.practice.module.mp.dal.mysql.message;

import com.practice.framework.common.pojo.PageResult;
import com.practice.framework.mybatis.core.mapper.BaseMapperX;
import com.practice.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.practice.module.mp.controller.admin.message.vo.message.MpMessagePageReqVO;
import com.practice.module.mp.dal.dataobject.message.MpMessageDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MpMessageMapper extends BaseMapperX<MpMessageDO> {

    default PageResult<MpMessageDO> selectPage(MpMessagePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MpMessageDO>()
                .eqIfPresent(MpMessageDO::getAccountId, reqVO.getAccountId())
                .eqIfPresent(MpMessageDO::getType, reqVO.getType())
                .eqIfPresent(MpMessageDO::getOpenid, reqVO.getOpenid())
                .betweenIfPresent(MpMessageDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MpMessageDO::getId));
    }

}
