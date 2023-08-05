package com.practice.module.system.controller.admin.information.vo;

import com.practice.framework.common.validation.Mobile;
import com.practice.module.system.controller.admin.usereducation.vo.UserEducationRespVO;
import com.practice.module.system.controller.admin.userskill.vo.UserSkillRespVO;
import com.practice.module.system.controller.admin.userwork.vo.UserWorkRespVO;
import com.practice.module.system.dal.dataobject.usereducation.UserEducationDO;
import com.practice.module.system.dal.dataobject.userskill.UserSkillDO;
import com.practice.module.system.dal.dataobject.userwork.UserWorkDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
public class InformationRespVO {

    @Schema(description = "用户姓名", required = true, example = "芋艿")
//    @Size(max = 30, message = "用户昵称长度不能超过30个字符")
    private String nickname;

    @Schema(description = "用户邮箱", example = "practice@iocoder.cn")
//    @Email(message = "邮箱格式不正确")
//    @Size(max = 50, message = "邮箱长度不能超过 50 个字符")
    private String email;

    @Schema(description = "手机号码", example = "15601691300")
    //@Mobile
    private String mobile;

    @Schema(description = "用户教育经历")
    private List<UserEducationRespVO> userEducation;

    @Schema(description = "用户技能")
    private List<UserSkillRespVO> userSkill;

    @Schema(description = "用户工作经历")
    private List<UserWorkRespVO> userWork;



}
