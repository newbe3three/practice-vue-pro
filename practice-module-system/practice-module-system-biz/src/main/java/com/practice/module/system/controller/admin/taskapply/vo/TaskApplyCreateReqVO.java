package com.practice.module.system.controller.admin.taskapply.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 任务申请创建 Request VO")
@Data
//@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TaskApplyCreateReqVO  {
    @Schema(description = "用户编号", required = true, example = "1")
    //@NotNull(message = "用户编号不能为空")
    private Long userId;

    @Schema(description = "用户所属企业编号", required = true, example = "A学院")
    //@NotNull(message = "用户部门名称不能为空")
    private Long companyId;

    @Schema(description = "用户名字", required = true, example = "张三")
    //@NotNull(message = "用户名字不能为空")
    private String name;



    @Schema(description = "任务id", required = true, example = "28559")
    @NotNull(message = "任务id不能为空")
    private Long taskId;

    @Schema(description = "手机号码")
    @NotNull(message = "手机号码不能为空")
    private String mobile;



}
