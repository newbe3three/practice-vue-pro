package com.practice.module.system.service.usereducation;

import java.util.*;
import javax.validation.*;
import com.practice.module.system.controller.admin.usereducation.vo.*;
import com.practice.module.system.dal.dataobject.usereducation.UserEducationDO;
import com.practice.framework.common.pojo.PageResult;

/**
 * 教育经历 Service 接口
 *
 * @author n3
 */
public interface UserEducationService {

    /**
     * 创建教育经历
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createUserEducation(@Valid UserEducationCreateReqVO createReqVO);

    /**
     * 更新教育经历
     *
     * @param updateReqVO 更新信息
     */
    void updateUserEducation(@Valid UserEducationUpdateReqVO updateReqVO);

    /**
     * 删除教育经历
     *
     * @param id 编号
     */
    void deleteUserEducation(Long id,Long userId);

    /**
     * 获得教育经历
     *
     * @param id 编号
     * @return 教育经历
     */
    UserEducationDO getUserEducation(Long id);

    /**
     * 获得教育经历列表
     *
     * @param ids 编号
     * @return 教育经历列表
     */
    List<UserEducationDO> getUserEducationList(Collection<Long> ids);

    /**
     * 获得教育经历分页
     *
     * @param pageReqVO 分页查询
     * @return 教育经历分页
     */
    PageResult<UserEducationDO> getUserEducationPage(UserEducationPageReqVO pageReqVO);

    /**
     * 获得教育经历列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 教育经历列表
     */
    List<UserEducationDO> getUserEducationList(UserEducationExportReqVO exportReqVO);

    /**
     * 获得教育经历
     *
     * @param userId 编号
     * @return 教育经历
     */
    List<UserEducationDO> getUserEducationWithUserId(Long userId);
}
