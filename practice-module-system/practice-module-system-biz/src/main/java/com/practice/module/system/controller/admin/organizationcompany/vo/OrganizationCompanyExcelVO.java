package com.practice.module.system.controller.admin.organizationcompany.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 社会企业 Excel VO
 *
 * @author wx
 */
@Data
public class OrganizationCompanyExcelVO {

    @ExcelProperty("企业编号")
    private Long id;

    @ExcelProperty("企业名称")
    private String name;

    @ExcelProperty("企业简称")
    private String abbreviation;

    @ExcelProperty("入口地址")
    private String ref;

    @ExcelProperty("联系邮箱")
    private String email;

    @ExcelProperty("联系电话")
    private String phone;

    @ExcelProperty("负责人")
    private String principal;

    @ExcelProperty("负责人性别")
    private String principalSex;

    @ExcelProperty("联系微信")
    private String principalWechat;

    @ExcelProperty("负责人职务")
    private String principalPosition;

    @ExcelProperty("统一社会信用代码")
    private String code;

    @ExcelProperty("地址")
    private String address;

    @ExcelProperty("注册地址")
    private String registeredAddress;

    @ExcelProperty("法人")
    private String legalPerson;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
