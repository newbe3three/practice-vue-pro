package com.practice.module.system.controller.admin.tenant.vo.tenant;

import com.practice.module.system.enums.DictTypeConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;
import com.practice.framework.excel.core.annotations.DictFormat;
import com.practice.framework.excel.core.convert.DictConvert;


/**
 * 租户 Excel VO
 *
 * @author 芋道源码
 */
@Data
public class TenantExcelVO {

    @ExcelProperty("租户编号")
    private Long id;

    @ExcelProperty("租户名")
    private String name;

    @ExcelProperty("联系人")
    private String contactName;

    @ExcelProperty("联系手机")
    private String contactMobile;

    @ExcelProperty("统一代码")
    @Schema(description = "统一代码", example = "15601691300")
    private String code;

    @ExcelProperty("地址")
    @Schema(description = "地址", example = "15601691300")
    private String address;

    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.COMMON_STATUS)
    private Integer status;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
