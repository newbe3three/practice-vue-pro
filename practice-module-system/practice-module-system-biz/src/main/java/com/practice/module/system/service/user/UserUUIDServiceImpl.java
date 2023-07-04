package com.practice.module.system.service.user;

import com.practice.module.system.controller.admin.user.vo.uuid.UUIDActivateReqVO;
import com.practice.module.system.controller.admin.user.vo.uuid.UUIDCreateReqVO;
import com.practice.module.system.convert.user.UUIDConvert;
import com.practice.module.system.dal.dataobject.user.AdminUserDO;
import com.practice.module.system.dal.dataobject.user.UserUUIDO;
import com.practice.module.system.dal.mysql.user.AdminUserMapper;
import com.practice.module.system.dal.mysql.user.UserUUIDMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.practice.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.practice.module.system.enums.ErrorCodeConstants.*;
import static com.practice.module.system.enums.ErrorCodeConstants.USER_NOT_EXISTS;

@Service("userUUIDService")
@Slf4j
public class UserUUIDServiceImpl implements UserUUIDService{
    @Resource
    private UserUUIDMapper userUUIDMapper;
    @Resource
    private AdminUserMapper adminUserMapper;
    @Resource
    private AdminUserService adminUserService;
    @Override
    public Long createUUID(UUIDCreateReqVO uuidCreateReqVO) {
        if(adminUserService.getUserByUsername(uuidCreateReqVO.getUsername()).getStatus() == 0) {
            throw exception(USER_INVITE_ERROR);
        }
        if (userUUIDMapper.selectOne("username",uuidCreateReqVO.getUsername()) != null ){
            throw exception(USER_INVITE_ERROR);
        }
        UserUUIDO userUUIDO = UUIDConvert.INSTANCE.convert(uuidCreateReqVO);
        userUUIDO.setStatus((byte) 0);
        userUUIDMapper.insert(userUUIDO);
        return userUUIDO.getId();
    }

    @Override
    public void activate(UUIDActivateReqVO uuidActivateReqVO) {
        //是否存在 uuid与username 都匹配的数据
        if(userUUIDMapper.selectOne("username",uuidActivateReqVO.getUsername(),
                "uuid",uuidActivateReqVO.getUuid())==null) {
            throw exception(USER_ACTIVATE_ERROR);
        }
        if (userUUIDMapper.selectOne("username",uuidActivateReqVO.getUsername(),
                "uuid",uuidActivateReqVO.getUuid()).getStatus() != 0) {
            throw exception(USER_ACTIVATE_ERROR);
        }

        AdminUserDO adminUserDO = adminUserService.getUserByUsername(uuidActivateReqVO.getUsername());

        if(adminUserDO.getStatus() != 1) {
            throw exception(USER_ACTIVATE_ERROR);
        }
        //修改状态
        adminUserDO.setStatus(0);
        adminUserMapper.updateById(adminUserDO);
        //修改uuid状态
        UserUUIDO convert = UUIDConvert.INSTANCE.convert(uuidActivateReqVO);
        convert.setStatus((byte) 1);
        System.out.println(convert);
        userUUIDMapper.updateByUserName(convert);
    }
}
