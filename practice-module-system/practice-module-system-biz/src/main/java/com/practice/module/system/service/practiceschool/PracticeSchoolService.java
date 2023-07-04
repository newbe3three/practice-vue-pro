package com.practice.module.system.service.practiceschool;

import java.util.*;
import javax.validation.*;
import com.practice.module.system.controller.admin.practiceschool.vo.*;
import com.practice.module.system.dal.dataobject.practiceschool.PracticeSchoolDO;
import com.practice.framework.common.pojo.PageResult;

/**
 * 学校申请实践 Service 接口
 *
 * @author n3
 */
public interface PracticeSchoolService {

    /**
     * 创建学校申请实践
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPracticeSchool(@Valid PracticeSchoolCreateReqVO createReqVO);

    /**
     * 更新学校申请实践
     *
     * @param updateReqVO 更新信息
     */
    void updatePracticeSchool(@Valid PracticeSchoolUpdateReqVO updateReqVO);

    /**
     * 删除学校申请实践
     *
     * @param id 编号
     */
    void deletePracticeSchool(Long id);

    /**
     * 获得学校申请实践
     *
     * @param id 编号
     * @return 学校申请实践
     */
    PracticeSchoolDO getPracticeSchool(Long id);

    /**
     * 获得学校申请实践列表
     *
     * @param ids 编号
     * @return 学校申请实践列表
     */
    List<PracticeSchoolDO> getPracticeSchoolList(Collection<Long> ids);

    /**
     * 获得学校申请实践分页
     *
     * @param pageReqVO 分页查询
     * @return 学校申请实践分页
     */
    PageResult<PracticeSchoolDO> getPracticeSchoolPage(PracticeSchoolPageReqVO pageReqVO);

    /**
     * 获得学校申请实践列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 学校申请实践列表
     */
    List<PracticeSchoolDO> getPracticeSchoolList(PracticeSchoolExportReqVO exportReqVO);





    /**
     * 学校发起实践申请
     *
     * @param createReqVO 申请信息
     * @return 编号
     */
    Long applyPracticeSchool(@Valid PracticeSchoolCreateReqVO createReqVO);

    /**
     * 企业确定学校
     *
     * @param practiceSchoolId 学校申请实践的id
     * @return null
     */
    void reviewPassPracticeApply(Long practiceSchoolId);
}
