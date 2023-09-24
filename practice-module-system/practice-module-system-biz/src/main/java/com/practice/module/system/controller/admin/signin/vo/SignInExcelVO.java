package com.practice.module.system.controller.admin.signin.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 签到 Excel VO
 *
 * @author 觅践
 */
@Data
public class SignInExcelVO {

    @ExcelProperty("编号")
    private Long id;

    @ExcelProperty("用户id")
    private Long userId;

    @ExcelProperty("实践id")
    private Long practiceId;

    @ExcelProperty("状态（默认为0， 已签到为1，已签退为2）")
    private Byte status;

    @ExcelProperty("签到结果（默认为0，迟到为1，早退2，迟到且早退为3，正常签到为4）")
    private Byte result;

    @ExcelProperty("纬度")
    private String lat;

    @ExcelProperty("经度")
    private String lon;

    @ExcelProperty("签到时间")
    private LocalDateTime signInTime;

    @ExcelProperty("签退时间")
    private LocalDateTime signOutTime;

    @ExcelProperty("签到地点")
    private String signInLocation;

    @ExcelProperty("签退地点")
    private String signOutLocation;

    @ExcelProperty("申请/创建时间")
    private LocalDateTime createTime;

}
