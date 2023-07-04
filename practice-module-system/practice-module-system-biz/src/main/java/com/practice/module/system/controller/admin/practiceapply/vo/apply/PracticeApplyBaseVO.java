package com.practice.module.system.controller.admin.practiceapply.vo.apply;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
* 实践申请 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class PracticeApplyBaseVO {

    @Schema(description = "用户id", example = "31785")
    private Long userId;

    @Schema(description = "申请实践id", required = true, example = "30503")
    @NotNull(message = "申请实践id不能为空")
    private Long practiceId;

    @Schema(description = "简历url", required = true)
    @NotNull(message = "简历url不能为空")
    private String resume;

    @Schema(description = "附加信息", required = true)
    @NotNull(message = "附加信息不能为空")
    private String message;

//    @Schema(description = "状态", example = "2")
//    private Byte status;



}
