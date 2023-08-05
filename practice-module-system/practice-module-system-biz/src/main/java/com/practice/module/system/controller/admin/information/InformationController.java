package com.practice.module.system.controller.admin.information;

import com.practice.framework.common.pojo.CommonResult;
import com.practice.module.system.controller.admin.information.vo.InformationRespVO;
import com.practice.module.system.controller.admin.userwork.vo.UserWorkRespVO;
import com.practice.module.system.convert.usereducation.UserEducationConvert;
import com.practice.module.system.convert.userskill.UserSkillConvert;
import com.practice.module.system.convert.userwork.UserWorkConvert;
import com.practice.module.system.dal.dataobject.user.AdminUserDO;
import com.practice.module.system.dal.dataobject.usereducation.UserEducationDO;
import com.practice.module.system.dal.dataobject.userskill.UserSkillDO;
import com.practice.module.system.dal.dataobject.userwork.UserWorkDO;
import com.practice.module.system.service.user.AdminUserService;
import com.practice.module.system.service.usereducation.UserEducationService;
import com.practice.module.system.service.userskill.UserSkillService;
import com.practice.module.system.service.userwork.UserWorkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.practice.framework.common.pojo.CommonResult.success;


@Tag(name = "管理后台 - 个人资料")
@RestController
@RequestMapping("/system/personal/information")
@Validated
public class InformationController {
    @Resource
    private UserEducationService userEducationService;
    @Resource
    private UserWorkService userWorkService;
    @Resource
    private UserSkillService userSkillService;
    @Resource
    private AdminUserService adminUserService;

    //获得用户个人资料
    @GetMapping("/get")
    @Operation(summary = "获得用户个人资料")
    @Parameter(name = "userId", description = "用户编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('')")
    public CommonResult<InformationRespVO> getUserInformation(@RequestParam("userId") Long userId) {
        AdminUserDO user = adminUserService.getUser(userId);
        List<UserEducationDO> userEducationDOList = userEducationService.getUserEducationWithUserId(userId);
        List<UserWorkDO> userWorkDOList =userWorkService.getUserWorkWithUserId(userId);
        List<UserSkillDO> userSkillDOList = userSkillService.getUserSkillWithUserId(userId);
        InformationRespVO informationRespVO = new InformationRespVO(user.getNickname(),user.getMobile(),user.getEmail(),
                UserEducationConvert.INSTANCE.convertList(userEducationDOList), UserSkillConvert.INSTANCE.convertList(userSkillDOList),
                UserWorkConvert.INSTANCE.convertList(userWorkDOList));

        return success(informationRespVO);
    }



}
