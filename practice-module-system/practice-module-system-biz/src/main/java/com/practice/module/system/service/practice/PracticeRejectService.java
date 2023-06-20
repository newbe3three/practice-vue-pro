package com.practice.module.system.service.practice;

import com.practice.module.system.dal.dataobject.practice.PracticeRejectDO;

import java.util.List;

/**
 * 实践审核驳回 Service 接口
 *
 * @author n3
 */
public interface PracticeRejectService {
    /**
     * 根据practiceId查询驳回记录列表
     *
     * @param practiceId 文章Id
     * @return 驳回记录列表
     */
    List<PracticeRejectDO> getPracticeRejectListByArticleId(Long practiceId);
}
