package com.practice.module.system.service.organizationother;

import com.practice.module.system.dal.dataobject.organizationcompany.OrganizationCompanyDO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.practice.module.system.controller.admin.organizationother.vo.*;
import com.practice.module.system.dal.dataobject.organizationother.OrganizationOtherDO;
import com.practice.framework.common.pojo.PageResult;

import com.practice.module.system.convert.organizationother.OrganizationOtherConvert;
import com.practice.module.system.dal.mysql.organizationother.OrganizationOtherMapper;

import static com.practice.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.practice.module.system.enums.ErrorCodeConstants.*;

/**
 * 其他组织 Service 实现类
 *
 * @author wx
 */
@Service
@Validated
public class OrganizationOtherServiceImpl implements OrganizationOtherService {

    @Resource
    private OrganizationOtherMapper organizationOtherMapper;

    @Override
    public Long createOrganizationOther(OrganizationOtherCreateReqVO createReqVO) {
        // 插入
        OrganizationOtherDO organizationOther = OrganizationOtherConvert.INSTANCE.convert(createReqVO);
        organizationOtherMapper.insert(organizationOther);
        // 返回
        return organizationOther.getId();
    }

    @Override
    public void updateOrganizationOther(OrganizationOtherUpdateReqVO updateReqVO) {
        // 校验存在
        validateOrganizationOtherExists(updateReqVO.getId());
        // 更新
        OrganizationOtherDO updateObj = OrganizationOtherConvert.INSTANCE.convert(updateReqVO);
        organizationOtherMapper.updateById(updateObj);
    }

    @Override
    public void deleteOrganizationOther(Long id) {
        // 校验存在
        validateOrganizationOtherExists(id);
        // 删除
        organizationOtherMapper.deleteById(id);
    }

    private void validateOrganizationOtherExists(Long id) {
        if (organizationOtherMapper.selectById(id) == null) {
            throw exception(ORGANIZATION_OTHER_NOT_EXISTS);
        }
    }

    @Override
    public OrganizationOtherDO getOrganizationOther(Long id) {
        return organizationOtherMapper.selectById(id);
    }

    @Override
    public List<OrganizationOtherDO> getOrganizationOtherList(Collection<Long> ids) {
        return organizationOtherMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<OrganizationOtherDO> getOrganizationOtherPage(OrganizationOtherPageReqVO pageReqVO) {
        return organizationOtherMapper.selectPage(pageReqVO);
    }

    @Override
    public List<OrganizationOtherDO> getOrganizationOtherList(OrganizationOtherExportReqVO exportReqVO) {
        return organizationOtherMapper.selectList(exportReqVO);
    }

    @Override
    public void stopServiceOrganizationOther(Long id) {
        // 校验存在
        validateOrganizationOtherExists(id);
        // 通过id查询道对应的元组
        OrganizationOtherDO organizationOtherDO = organizationOtherMapper.selectById(id);
        if (organizationOtherMapper.selectById(organizationOtherDO.getId()).getIsEnd()) {
            throw exception(ORGANIZATION_COMPANY_ALREADY_STOP);
        }
        organizationOtherDO.setIsEnd(true);
        organizationOtherMapper.updateById(organizationOtherDO);
    }

}
