package com.practice.module.system.controller.admin.practiceschool.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 学校申请实践创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PracticeSchoolCreateReqVO extends PracticeSchoolBaseVO {

}
