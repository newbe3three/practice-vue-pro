package com.practice.module.system.framework.datapermission.config;

import com.practice.module.system.dal.dataobject.dept.DeptDO;
import com.practice.module.system.dal.dataobject.task.TaskDO;
import com.practice.module.system.dal.dataobject.user.AdminUserDO;
import com.practice.framework.datapermission.core.rule.dept.DeptDataPermissionRuleCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * system 模块的数据权限 Configuration
 *
 * @author 芋道源码
 */
@Configuration(proxyBeanMethods = false)
public class DataPermissionConfiguration {

    @Bean
    public DeptDataPermissionRuleCustomizer sysDeptDataPermissionRuleCustomizer() {
        return rule -> {
            // dept
            rule.addDeptColumn(AdminUserDO.class);
            rule.addDeptColumn(DeptDO.class, "id");
            // user

            rule.addUserColumn(AdminUserDO.class, "id");
            //能查看所有的任务，但是只能操作本人的任务
            //rule.addUserColumn(TaskDO.class,"creator");
        };
    }

}
