package com.practice.module.system.dal.dataobject.signin;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.practice.framework.mybatis.core.dataobject.BaseDO;

/**
 * 签到 DO
 *
 * @author 觅践
 */
@TableName("system_sign_in")
@KeySequence("system_sign_in_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 实践id
     */
    private Long practiceId;
    /**
     * 状态（默认为0， 已签到为1，已签退为2）
     */
    private Byte status;
    /**
     * 签到结果（默认为0，迟到为1，早退2，迟到且早退为3，正常签到为4）
     */
    private Byte result;
    /**
     * 纬度
     */
    private String lat;
    /**
     * 经度
     */
    private String lon;
    /**
     * 签到时间
     */
    private LocalDateTime signInTime;
    /**
     * 签退时间
     */
    private LocalDateTime signOutTime;
    /**
     * 签到地点
     */
    private String signInLocation;
    /**
     * 签退地点
     */
    private String signOutLocation;

}
