package com.practice.module.system.service.signin;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.*;
import com.practice.module.system.controller.admin.signin.vo.*;
import com.practice.module.system.dal.dataobject.signin.SignInDO;
import com.practice.framework.common.pojo.PageResult;

import com.practice.module.system.convert.signin.SignInConvert;
import com.practice.module.system.dal.mysql.signin.SignInMapper;

import static com.practice.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.practice.module.system.enums.ErrorCodeConstants.*;

/**
 * 签到 Service 实现类
 *
 * @author 觅践
 */
@Service
@Validated
public class SignInServiceImpl implements SignInService {

    @Resource
    private SignInMapper signInMapper;

    @Override
    public SignInDO createSignIn(SignInCreateReqVO createReqVO) {

        // 状态修改为1，表示已经签到
        createReqVO.setStatus((byte) 1);
        // 判断签到时间
        // 1、通过实践id 获得实践签到开始时间
        // 2、判断当前时间，标记签到状态 是否迟到
        // 插入
        SignInDO signIn = SignInConvert.INSTANCE.convert(createReqVO);
        signInMapper.insert(signIn);
        // 返回
        return signIn;
    }

    @Override
    public SignInDO updateSignOut(SignInUpdateReqVO updateReqVO) {
        // 校验存在
        validateSignInExists(updateReqVO.getId());
        // 状态修改为2 表示已经签退
        updateReqVO.setStatus((byte) 2);
        // 1、通过实践id 获得实践签到开始时间
        // 2、判断当前时间，标记签到状态 是否早退
        // 更新
        SignInDO updateObj = SignInConvert.INSTANCE.convert(updateReqVO);
        signInMapper.updateById(updateObj);
        return signInMapper.selectById(updateReqVO.getId());
    }

    @Override
    public void deleteSignIn(Long id) {
        // 校验存在
        validateSignInExists(id);
        // 删除
        signInMapper.deleteById(id);
    }

    private void validateSignInExists(Long id) {
        if (signInMapper.selectById(id) == null) {
            throw exception(SIGN_IN_NOT_EXISTS);
        }
    }

    @Override
    public SignInDO getSignIn(Long id) {
        return signInMapper.selectById(id);
    }

    @Override
    public List<SignInDO> getSignInList(Collection<Long> ids) {
        return signInMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<SignInDO> getSignInPage(SignInPageReqVO pageReqVO) {
        return signInMapper.selectPage(pageReqVO);
    }

    @Override
    public List<SignInDO> getSignInList(SignInExportReqVO exportReqVO) {
        return signInMapper.selectList(exportReqVO);
    }

    @Override
    public SignInDO querySignIn(SignInQueryByIdVO createReqVO) {
        return signInMapper.selectLastDate(createReqVO);
    }

}
