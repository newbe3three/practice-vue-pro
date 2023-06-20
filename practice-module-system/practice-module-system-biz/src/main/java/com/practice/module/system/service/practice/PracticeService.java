package com.practice.module.system.service.practice;

import java.util.*;
import javax.validation.*;

import com.practice.module.system.controller.admin.practice.vo.practice.PracticeCreateReqVO;
import com.practice.module.system.controller.admin.practice.vo.practice.PracticeExportReqVO;
import com.practice.module.system.controller.admin.practice.vo.practice.PracticePageReqVO;
import com.practice.module.system.controller.admin.practice.vo.practice.PracticeUpdateReqVO;
import com.practice.module.system.controller.admin.practice.vo.reject.PracticeRejectCreateReqVO;
import com.practice.module.system.dal.dataobject.practice.PracticeDO;
import com.practice.framework.common.pojo.PageResult;

/**
 * 实践 Service 接口
 *
 * @author n3
 */
public interface PracticeService {

    /**
     * 创建实践
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPractice(@Valid PracticeCreateReqVO createReqVO);

    /**
     * 更新实践
     *
     * @param updateReqVO 更新信息
     */
    void updatePracticeApply(@Valid PracticeUpdateReqVO updateReqVO);

    /**
     * 删除实践
     *
     * @param id 编号
     */
    void deletePractice(Long id);

    /**
     * 获得实践
     *
     * @param id 编号
     * @return 实践
     */
    PracticeDO getPractice(Long id);

    /**
     * 获得实践列表
     *
     * @param ids 编号
     * @return 实践列表
     */
    List<PracticeDO> getPracticeList(Collection<Long> ids);

    /**
     * 获得实践分页
     *
     * @param pageReqVO 分页查询
     * @return 实践分页
     */
    PageResult<PracticeDO> getPracticePage(PracticePageReqVO pageReqVO);

    /**
     * 获得实践列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 实践列表
     */
    List<PracticeDO> getPracticeList(PracticeExportReqVO exportReqVO);

    void reviewPassPractice(Long id);

    /**
     * 实践申请驳回
     *
     * @param createReqVO 实践申请驳回意见以及文章编号
     * @return
     */
    void reviewFailurePractice(PracticeRejectCreateReqVO createReqVO);


}
