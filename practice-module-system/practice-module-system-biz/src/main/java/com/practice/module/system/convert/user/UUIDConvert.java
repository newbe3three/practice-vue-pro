package com.practice.module.system.convert.user;

import com.practice.module.system.controller.admin.user.vo.uuid.UUIDActivateReqVO;
import com.practice.module.system.controller.admin.user.vo.uuid.UUIDCreateReqVO;
import com.practice.module.system.dal.dataobject.user.UserUUIDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UUIDConvert {
    UUIDConvert INSTANCE = Mappers.getMapper(UUIDConvert.class);

    UserUUIDO convert(UUIDCreateReqVO bean);
    UserUUIDO convert(UUIDActivateReqVO bean);
}
