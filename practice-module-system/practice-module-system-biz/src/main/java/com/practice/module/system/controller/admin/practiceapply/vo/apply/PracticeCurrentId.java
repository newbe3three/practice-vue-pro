package com.practice.module.system.controller.admin.practiceapply.vo.apply;

import com.practice.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 实践申请分页 Request VO")
@Data
public class PracticeCurrentId {
    @Schema(description = "用户id", example = "31785")
    private Long userId;

    @Schema(description = "状态", example = "2")
    private Byte status;

}
