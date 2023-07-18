package com.practice.module.system.controller.admin.practice.vo.practice;

import com.practice.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PracticeIdPageReqVO extends PageParam {

    @Schema(description = "实践编号列表")
    private List<Long> practiceIdList;
}
