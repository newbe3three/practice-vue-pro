package com.practice.module.system.service.user;


import com.practice.module.system.controller.admin.user.vo.uuid.UUIDActivateReqVO;
import com.practice.module.system.controller.admin.user.vo.uuid.UUIDCreateReqVO;

import javax.validation.Valid;

public interface UserUUIDService {

    Long createUUID(@Valid UUIDCreateReqVO uuidCreateReqVO);

    void activate(@Valid UUIDActivateReqVO uuidActivateReqVO);
}
