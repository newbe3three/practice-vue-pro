package com.practice.module.infra.controller.admin.file.vo.file;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 文件 Response VO,不返回 content 字段，太大")
@Data
public class FileRespRVO {
    @Schema(description = "文件路径", required = true, example = "practice.jpg")
    private String path;

    @Schema(description = "原文件名", required = true, example = "practice.jpg")
    private String name;
}
