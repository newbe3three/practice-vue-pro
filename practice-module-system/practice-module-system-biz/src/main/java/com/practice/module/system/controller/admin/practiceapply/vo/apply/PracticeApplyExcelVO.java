package com.practice.module.system.controller.admin.practiceapply.vo.apply;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;
import com.practice.framework.excel.core.annotations.DictFormat;
import com.practice.framework.excel.core.convert.DictConvert;


/**
 * 实践申请 Excel VO
 *
 * @author n3
 */
@Data
public class PracticeApplyExcelVO {

    @ExcelProperty("实践申请编号")
    private Long id;

    @ExcelProperty("用户id")
    private Long userId;

    @ExcelProperty("简历url")
    private String resume;

    @ExcelProperty("附加信息")
    private String message;

    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("practice_apply_status") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Byte status;

    @ExcelProperty("申请/创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("申请实践id")
    private Long practiceId;

}
