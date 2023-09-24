package com.practice.module.system.dal.mysql.signin;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.practice.framework.common.pojo.PageResult;
import com.practice.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.practice.framework.mybatis.core.mapper.BaseMapperX;
import com.practice.module.system.dal.dataobject.signin.SignInDO;
import org.apache.ibatis.annotations.Mapper;
import com.practice.module.system.controller.admin.signin.vo.*;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 签到 Mapper
 *
 * @author 觅践
 */
@Mapper
public interface SignInMapper extends BaseMapperX<SignInDO> {

    default PageResult<SignInDO> selectPage(SignInPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SignInDO>()
                .eqIfPresent(SignInDO::getUserId, reqVO.getUserId())
                .eqIfPresent(SignInDO::getPracticeId, reqVO.getPracticeId())
                .eqIfPresent(SignInDO::getStatus, reqVO.getStatus())
                .eqIfPresent(SignInDO::getResult, reqVO.getResult())
                .likeIfPresent(SignInDO::getSignInLocation, reqVO.getSignInLocation())
                .likeIfPresent(SignInDO::getSignOutLocation, reqVO.getSignOutLocation())
                .betweenIfPresent(SignInDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SignInDO::getId));
    }

    default List<SignInDO> selectList(SignInExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<SignInDO>()
                .eqIfPresent(SignInDO::getUserId, reqVO.getUserId())
                .eqIfPresent(SignInDO::getPracticeId, reqVO.getPracticeId())
                .eqIfPresent(SignInDO::getStatus, reqVO.getStatus())
                .eqIfPresent(SignInDO::getResult, reqVO.getResult())
                .likeIfPresent(SignInDO::getSignInLocation, reqVO.getSignInLocation())
                .likeIfPresent(SignInDO::getSignOutLocation, reqVO.getSignOutLocation())
                .betweenIfPresent(SignInDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SignInDO::getId));
    }

    default Byte selectByDateAndId(SignInCreateReqVO reqVO){
        LocalDate now = LocalDate.now();
        SignInDO signInDO = selectOne(new LambdaQueryWrapperX<SignInDO>()
                .eqIfPresent(SignInDO::getUserId, reqVO.getUserId())
                .eqIfPresent(SignInDO::getPracticeId, reqVO.getPracticeId())
                .eqIfPresent(SignInDO::getCreateTime, now));
        return signInDO.getStatus();
    }

    default SignInDO selectLastDate(SignInQueryByIdVO reqVO){
        return selectOne(new LambdaQueryWrapperX<SignInDO>()
                .eqIfPresent(SignInDO::getUserId, reqVO.getUserId())
                .eqIfPresent(SignInDO::getPracticeId, reqVO.getPracticeId())
                .orderByDesc(SignInDO::getId)
                .last("limit 1"));
    }


}
