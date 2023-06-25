package com.practice.module.system.service.organizationcompany;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.practice.module.system.controller.admin.organizationcompany.vo.*;
import com.practice.module.system.dal.dataobject.organizationcompany.OrganizationCompanyDO;
import com.practice.framework.common.pojo.PageResult;

import com.practice.module.system.convert.organizationcompany.OrganizationCompanyConvert;
import com.practice.module.system.dal.mysql.organizationcompany.OrganizationCompanyMapper;

import static com.practice.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.practice.module.system.enums.ErrorCodeConstants.*;

/**
 * 社会企业 Service 实现类
 *
 * @author wx
 */
@Service
@Validated
public class OrganizationCompanyServiceImpl implements OrganizationCompanyService {

    @Resource
    private OrganizationCompanyMapper organizationCompanyMapper;

    @Override
    public Long createOrganizationCompany(OrganizationCompanyCreateReqVO createReqVO) {
        // 插入
        OrganizationCompanyDO organizationCompany = OrganizationCompanyConvert.INSTANCE.convert(createReqVO);
        organizationCompanyMapper.insert(organizationCompany);
        // 返回
        return organizationCompany.getId();
    }

    @Override
    public void updateOrganizationCompany(OrganizationCompanyUpdateReqVO updateReqVO) {
        // 校验存在
        validateOrganizationCompanyExists(updateReqVO.getId());
        // 更新
        OrganizationCompanyDO updateObj = OrganizationCompanyConvert.INSTANCE.convert(updateReqVO);
        organizationCompanyMapper.updateById(updateObj);
    }

    @Override
    public void deleteOrganizationCompany(Long id) {
        // 校验存在
        validateOrganizationCompanyExists(id);
        // 删除
        organizationCompanyMapper.deleteById(id);
    }

    private void validateOrganizationCompanyExists(Long id) {
        if (organizationCompanyMapper.selectById(id) == null) {
            throw exception(ORGANIZATION_COMPANY_NOT_EXISTS);
        }
    }

    @Override
    public OrganizationCompanyDO getOrganizationCompany(Long id) {
        return organizationCompanyMapper.selectById(id);
    }

    @Override
    public List<OrganizationCompanyDO> getOrganizationCompanyList(Collection<Long> ids) {
        return organizationCompanyMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<OrganizationCompanyDO> getOrganizationCompanyPage(OrganizationCompanyPageReqVO pageReqVO) {
        return organizationCompanyMapper.selectPage(pageReqVO);
    }

    @Override
    public List<OrganizationCompanyDO> getOrganizationCompanyList(OrganizationCompanyExportReqVO exportReqVO) {
        return organizationCompanyMapper.selectList(exportReqVO);
    }

}
