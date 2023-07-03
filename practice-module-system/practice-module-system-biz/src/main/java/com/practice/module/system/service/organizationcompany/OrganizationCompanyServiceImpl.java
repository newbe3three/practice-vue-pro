package com.practice.module.system.service.organizationcompany;

import cn.hutool.core.collection.CollectionUtil;
import com.practice.framework.common.enums.CommonStatusEnum;
import com.practice.module.system.controller.admin.sms.vo.template.SmsTemplateSendReqVO;
import com.practice.module.system.controller.admin.user.vo.user.UserCreateReqVO;
import com.practice.module.system.convert.task.TaskConvert;
import com.practice.module.system.convert.user.UserConvert;
import com.practice.module.system.dal.dataobject.dept.UserPostDO;
import com.practice.module.system.dal.dataobject.task.TaskDO;
import com.practice.module.system.dal.dataobject.user.AdminUserDO;
import com.practice.module.system.dal.mysql.user.AdminUserMapper;
import com.practice.module.system.service.sms.SmsSendService;
import com.practice.module.system.service.sms.SmsTemplateService;
import com.practice.module.system.service.tenant.TenantService;
import com.practice.module.system.service.user.AdminUserService;
import org.springframework.context.annotation.Lazy;
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
import static com.practice.framework.common.util.collection.CollectionUtils.convertList;
import static com.practice.module.system.enums.ErrorCodeConstants.*;

/**
 * 社会企业 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class OrganizationCompanyServiceImpl implements OrganizationCompanyService {
    private static String TemplateCode = "MySms";

    @Resource
    private OrganizationCompanyMapper organizationCompanyMapper;

    @Resource
    private AdminUserMapper userMapper;

    @Resource
    private SmsSendService smsSendService;

    @Resource
    private SmsTemplateService smsTemplateService;

    @Resource
    private AdminUserService userService;

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
    public void updateSignOrganizationCompany(OrganizationCompanyUpdateReqVO updateReqVO) {
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

    @Override
    public void stopServiceOrganizationCompany(Long id) {
        // 校验存在
        validateOrganizationCompanyExists(id);
        // 通过id查询道对应的元组
        OrganizationCompanyDO organizationCompanyDO = organizationCompanyMapper.selectById(id);
        if (organizationCompanyMapper.selectById(organizationCompanyDO.getId()).getIsEnd()) {
            throw exception(ORGANIZATION_COMPANY_ALREADY_STOP);
        }
        organizationCompanyDO.setIsEnd(true);
        organizationCompanyMapper.updateById(organizationCompanyDO);
    }

    @Override
    public void sendInviteOrganizationCompany(Long id) {
        // 校验存在
        validateOrganizationCompanyExists(id);
        // 通过id查询道对应的元组
        OrganizationCompanyDO organizationCompanyDO = organizationCompanyMapper.selectById(id);
        UserCreateReqVO userCreateReqVO = new UserCreateReqVO();
        userCreateReqVO.setUsername("QY" + organizationCompanyDO.getId());
        userCreateReqVO.setPassword(organizationCompanyDO.getPhone());
        userCreateReqVO.setNickname("QY" + organizationCompanyDO.getId());
        userCreateReqVO.setEmail(organizationCompanyDO.getEmail());
        userService.addUser(userCreateReqVO);

        // 发送短信邀请
        SmsTemplateSendReqVO sendReqVO = new SmsTemplateSendReqVO();
        sendReqVO.setMobile(organizationCompanyDO.getPhone());
        sendReqVO.setTemplateCode(TemplateCode);
//        List<String> list=smsTemplateService.getSmsTemplate(0L).getParams();
        Map<String, Object> map= new HashMap<String, Object>();
//        list.forEach(item->{
//            map.put(item, "1243");
//        });
        // todo 验证码替换为账号 和 初始密码 已经对应的网址
        map.put("code", "1212");
        sendReqVO.setTemplateParams(map);
        smsSendService.sendSingleSmsToAdmin(organizationCompanyDO.getPhone(), null,
                TemplateCode, sendReqVO.getTemplateParams());
    }

}
