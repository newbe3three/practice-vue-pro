package com.practice.module.system.service.signin;

import java.util.*;
import javax.validation.*;
import com.practice.module.system.controller.admin.signin.vo.*;
import com.practice.module.system.dal.dataobject.signin.SignInDO;
import com.practice.framework.common.pojo.PageResult;

/**
 * 签到 Service 接口
 *
 * @author 觅践
 */
public interface SignInService {

    /**
     * 创建签到
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    SignInDO createSignIn(@Valid SignInCreateReqVO createReqVO);

    /**
     * 更新签到
     *
     * @param updateReqVO 更新信息
     */
    SignInDO updateSignOut(@Valid SignInUpdateReqVO updateReqVO);

    /**
     * 删除签到
     *
     * @param id 编号
     */
    void deleteSignIn(Long id);

    /**
     * 获得签到
     *
     * @param id 编号
     * @return 签到
     */
    SignInDO getSignIn(Long id);

    /**
     * 获得签到列表
     *
     * @param ids 编号
     * @return 签到列表
     */
    List<SignInDO> getSignInList(Collection<Long> ids);

    /**
     * 获得签到分页
     *
     * @param pageReqVO 分页查询
     * @return 签到分页
     */
    PageResult<SignInDO> getSignInPage(SignInPageReqVO pageReqVO);

    /**
     * 获得签到列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 签到列表
     */
    List<SignInDO> getSignInList(SignInExportReqVO exportReqVO);

    SignInDO querySignIn(SignInQueryByIdVO createReqVO);
}
