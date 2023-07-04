package com.practice.module.system.dal.dataobject.user;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.practice.framework.tenant.core.db.TenantBaseDO;
import lombok.*;

@TableName(value = "system_uuid", autoResultMap = true) // 由于 SQL Server 的 system_user 是关键字，所以使用 system_users
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUUIDO extends TenantBaseDO {
    /**
     * 用户ID
     */
    @TableId
    private Long id;
    /**
     * 用户账号
     */
    private String username;

    private String uuid;

    private Byte status;
}
