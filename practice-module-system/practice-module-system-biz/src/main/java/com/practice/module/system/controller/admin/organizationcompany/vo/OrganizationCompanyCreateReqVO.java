package com.practice.module.system.controller.admin.organizationcompany.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 社会企业创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrganizationCompanyCreateReqVO extends OrganizationCompanyBaseVO {

}
