package com.practice.module.system.service.practice;

import com.practice.module.system.dal.dataobject.practice.PracticeRejectDO;
import com.practice.module.system.dal.dataobject.resourcearticle.ResourceArticleRejectDO;
import com.practice.module.system.dal.mysql.practice.PracticeRejectMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

/**
 * 实践审核驳回 Service 实现类
 *
 * @author n3
 */
@Service
@Validated
public class PracticeRejectServiceImpl implements PracticeRejectService{
    @Resource
    private PracticeRejectMapper practiceRejectMapper;

    @Override
    public List<PracticeRejectDO> getPracticeRejectListByArticleId(Long practiceId) {
        return practiceRejectMapper.selectList("practice_id", practiceId);
    }
}
