package com.practice.module.system.controller.admin.practiceschool.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
* 学校申请实践 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class PracticeSchoolBaseVO {

    @Schema(description = "申请学校id", example = "2828")
    private Long schoolId;

    @Schema(description = "申请实践id", required = true, example = "11135")
    @NotNull(message = "申请实践id不能为空")
    private Long practiceId;

//    @Schema(description = "状态", example = "2")
//    private Byte status;

}
