package com.practice.module.system.dal.mysql.user;

import com.practice.framework.mybatis.core.mapper.BaseMapperX;
import com.practice.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.practice.module.system.dal.dataobject.user.AdminUserDO;
import com.practice.module.system.dal.dataobject.user.UserUUIDO;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserUUIDMapper extends BaseMapperX<UserUUIDO> {
    default int updateByUserName(UserUUIDO userUUIDO) {

       return update(userUUIDO,new LambdaQueryWrapperX<UserUUIDO>()
                .eqIfPresent(UserUUIDO::getUsername,userUUIDO.getUsername()));

    }

}
