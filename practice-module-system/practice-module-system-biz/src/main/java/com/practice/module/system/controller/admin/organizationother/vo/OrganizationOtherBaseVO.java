package com.practice.module.system.controller.admin.organizationother.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
* 其他组织 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class OrganizationOtherBaseVO {

    @Schema(description = "组织名称", required = true, example = "张三")
    @NotNull(message = "组织名称不能为空")
    private String name;

    @Schema(description = "组织简称")
    private String abbreviation;

    @Schema(description = "入口地址")
    private String ref;

    @Schema(description = "联系邮箱")
    private String email;

    @Schema(description = "联系电话", required = true)
    @NotNull(message = "联系电话不能为空")
    private String phone;

    @Schema(description = "负责人", required = true)
    @NotNull(message = "负责人不能为空")
    private String principal;

    @Schema(description = "联系微信")
    private String principalWechat;

    @Schema(description = "负责人职务")
    private String principalPosition;

    @Schema(description = "统一社会信用代码", required = true)
    @NotNull(message = "统一社会信用代码不能为空")
    private String code;

    @Schema(description = "地址", required = true)
    @NotNull(message = "地址不能为空")
    private String address;

    @Schema(description = "开始时间")
    private String startTime;

    @Schema(description = "终止时间")
    private String endTime;

    @Schema(description = "是否终止服务")
    private Boolean isEnd;


}
