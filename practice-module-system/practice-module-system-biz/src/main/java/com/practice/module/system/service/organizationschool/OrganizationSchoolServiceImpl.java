package com.practice.module.system.service.organizationschool;

import com.practice.module.system.dal.dataobject.organizationother.OrganizationOtherDO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.practice.module.system.controller.admin.organizationschool.vo.*;
import com.practice.module.system.dal.dataobject.organizationschool.OrganizationSchoolDO;
import com.practice.framework.common.pojo.PageResult;

import com.practice.module.system.convert.organizationschool.OrganizationSchoolConvert;
import com.practice.module.system.dal.mysql.organizationschool.OrganizationSchoolMapper;

import static com.practice.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.practice.module.system.enums.ErrorCodeConstants.*;

/**
 * 教育院校 Service 实现类
 *
 * @author wx
 */
@Service
@Validated
public class OrganizationSchoolServiceImpl implements OrganizationSchoolService {

    @Resource
    private OrganizationSchoolMapper organizationSchoolMapper;

    @Override
    public Long createOrganizationSchool(OrganizationSchoolCreateReqVO createReqVO) {
        // 插入
        OrganizationSchoolDO organizationSchool = OrganizationSchoolConvert.INSTANCE.convert(createReqVO);
        organizationSchoolMapper.insert(organizationSchool);
        // 返回
        return organizationSchool.getId();
    }

    @Override
    public void updateOrganizationSchool(OrganizationSchoolUpdateReqVO updateReqVO) {
        // 校验存在
        validateOrganizationSchoolExists(updateReqVO.getId());
        // 更新
        OrganizationSchoolDO updateObj = OrganizationSchoolConvert.INSTANCE.convert(updateReqVO);
        organizationSchoolMapper.updateById(updateObj);
    }

    @Override
    public void deleteOrganizationSchool(Long id) {
        // 校验存在
        validateOrganizationSchoolExists(id);
        // 删除
        organizationSchoolMapper.deleteById(id);
    }

    private void validateOrganizationSchoolExists(Long id) {
        if (organizationSchoolMapper.selectById(id) == null) {
            throw exception(ORGANIZATION_SCHOOL_NOT_EXISTS);
        }
    }

    @Override
    public OrganizationSchoolDO getOrganizationSchool(Long id) {
        return organizationSchoolMapper.selectById(id);
    }

    @Override
    public List<OrganizationSchoolDO> getOrganizationSchoolList(Collection<Long> ids) {
        return organizationSchoolMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<OrganizationSchoolDO> getOrganizationSchoolPage(OrganizationSchoolPageReqVO pageReqVO) {
        return organizationSchoolMapper.selectPage(pageReqVO);
    }

    @Override
    public List<OrganizationSchoolDO> getOrganizationSchoolList(OrganizationSchoolExportReqVO exportReqVO) {
        return organizationSchoolMapper.selectList(exportReqVO);
    }

    @Override
    public void stopServiceOrganizationSchool(Long id) {
        // 校验存在
        validateOrganizationSchoolExists(id);
        // 通过id查询道对应的元组
        OrganizationSchoolDO organizationSchoolDO = organizationSchoolMapper.selectById(id);
        if (organizationSchoolMapper.selectById(organizationSchoolDO.getId()).getIsEnd()) {
            throw exception(ORGANIZATION_COMPANY_ALREADY_STOP);
        }
        organizationSchoolDO.setIsEnd(true);
        organizationSchoolMapper.updateById(organizationSchoolDO);
    }

    @Override
    public void updateSignOrganizationSchool(OrganizationSchoolUpdateReqVO updateReqVO) {
        // 校验存在
        validateOrganizationSchoolExists(updateReqVO.getId());
        // 更新
        OrganizationSchoolDO updateObj = OrganizationSchoolConvert.INSTANCE.convert(updateReqVO);
        organizationSchoolMapper.updateById(updateObj);
    }

}
