package com.practice.module.system.service.practiceapply;

import com.practice.module.system.controller.admin.practiceapply.vo.apply.PracticeApplyCreateReqVO;
import com.practice.module.system.controller.admin.practiceapply.vo.apply.PracticeApplyExportReqVO;
import com.practice.module.system.controller.admin.practiceapply.vo.apply.PracticeApplyPageReqVO;
import com.practice.module.system.controller.admin.practiceapply.vo.apply.PracticeApplyUpdateReqVO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import com.practice.framework.test.core.ut.BaseDbUnitTest;

import com.practice.module.system.dal.dataobject.practiceapply.PracticeApplyDO;
import com.practice.module.system.dal.mysql.practiceapply.PracticeApplyMapper;
import com.practice.framework.common.pojo.PageResult;

import org.springframework.context.annotation.Import;
import java.util.*;

import static com.practice.module.system.enums.ErrorCodeConstants.*;
import static com.practice.framework.test.core.util.AssertUtils.*;
import static com.practice.framework.test.core.util.RandomUtils.*;
import static com.practice.framework.common.util.date.LocalDateTimeUtils.*;
import static com.practice.framework.common.util.object.ObjectUtils.*;
import static org.junit.jupiter.api.Assertions.*;

/**
* {@link PracticeApplyServiceImpl} 的单元测试类
*
* @author n3
*/
@Import(PracticeApplyServiceImpl.class)
public class PracticeApplyServiceImplTest extends BaseDbUnitTest {

    @Resource
    private PracticeApplyServiceImpl practiceApplyService;

    @Resource
    private PracticeApplyMapper practiceApplyMapper;

    //c @Test
//    public void testCreatePracticeApply_success() {
//        // 准备参数
//        PracticeApplyDO reqVO = randomPojo(PracticeApplyCreateReqVO.class);
//
//        // 调用
//        Long practiceApplyId = practiceApplyService.createPracticeApply(reqVO);
//        // 断言
//        assertNotNull(practiceApplyId);
//        // 校验记录的属性是否正确
//        PracticeApplyDO practiceApply = practiceApplyMapper.selectById(practiceApplyId);
//        assertPojoEquals(reqVO, practiceApply);
//    }

//    @Test
//    public void testUpdatePracticeApply_success() {
//        // mock 数据
//        PracticeApplyDO dbPracticeApply = randomPojo(PracticeApplyDO.class);
//        practiceApplyMapper.insert(dbPracticeApply);// @Sql: 先插入出一条存在的数据
//        // 准备参数
//        PracticeApplyUpdateReqVO reqVO = randomPojo(PracticeApplyUpdateReqVO.class, o -> {
//            o.setId(dbPracticeApply.getId()); // 设置更新的 ID
//        });
//
//        // 调用
//        practiceApplyService.updatePracticeApply(reqVO);
//        // 校验是否更新正确
//        PracticeApplyDO practiceApply = practiceApplyMapper.selectById(reqVO.getId()); // 获取最新的
//        assertPojoEquals(reqVO, practiceApply);
//    }

//    @Test
//    public void testUpdatePracticeApply_notExists() {
//        // 准备参数
//        PracticeApplyUpdateReqVO reqVO = randomPojo(PracticeApplyUpdateReqVO.class);
//
//        // 调用, 并断言异常
//        assertServiceException(() -> practiceApplyService.updatePracticeApply(reqVO), PRACTICE_APPLY_NOT_EXISTS);
//    }

    @Test
    public void testDeletePracticeApply_success() {
        // mock 数据
        PracticeApplyDO dbPracticeApply = randomPojo(PracticeApplyDO.class);
        practiceApplyMapper.insert(dbPracticeApply);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbPracticeApply.getId();

        // 调用
        practiceApplyService.deletePracticeApply(id);
       // 校验数据不存在了
       assertNull(practiceApplyMapper.selectById(id));
    }

    @Test
    public void testDeletePracticeApply_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> practiceApplyService.deletePracticeApply(id), PRACTICE_APPLY_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetPracticeApplyPage() {
       // mock 数据
       PracticeApplyDO dbPracticeApply = randomPojo(PracticeApplyDO.class, o -> { // 等会查询到
           o.setUserId(null);
           o.setResume(null);
           o.setMessage(null);
           o.setStatus(null);
           o.setCreateTime(null);
           o.setPracticeId(null);
       });
       practiceApplyMapper.insert(dbPracticeApply);
       // 测试 userId 不匹配
       practiceApplyMapper.insert(cloneIgnoreId(dbPracticeApply, o -> o.setUserId(null)));
       // 测试 resume 不匹配
       practiceApplyMapper.insert(cloneIgnoreId(dbPracticeApply, o -> o.setResume(null)));
       // 测试 message 不匹配
       practiceApplyMapper.insert(cloneIgnoreId(dbPracticeApply, o -> o.setMessage(null)));
       // 测试 status 不匹配
       practiceApplyMapper.insert(cloneIgnoreId(dbPracticeApply, o -> o.setStatus(null)));
       // 测试 createTime 不匹配
       practiceApplyMapper.insert(cloneIgnoreId(dbPracticeApply, o -> o.setCreateTime(null)));
       // 测试 practiceId 不匹配
       practiceApplyMapper.insert(cloneIgnoreId(dbPracticeApply, o -> o.setPracticeId(null)));
       // 准备参数
       PracticeApplyPageReqVO reqVO = new PracticeApplyPageReqVO();
       reqVO.setUserId(null);
       reqVO.setResume(null);
       reqVO.setMessage(null);
       reqVO.setStatus(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setPracticeId(null);

       // 调用
       PageResult<PracticeApplyDO> pageResult = practiceApplyService.getPracticeApplyPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbPracticeApply, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetPracticeApplyList() {
       // mock 数据
       PracticeApplyDO dbPracticeApply = randomPojo(PracticeApplyDO.class, o -> { // 等会查询到
           o.setUserId(null);
           o.setResume(null);
           o.setMessage(null);
           o.setStatus(null);
           o.setCreateTime(null);
           o.setPracticeId(null);
       });
       practiceApplyMapper.insert(dbPracticeApply);
       // 测试 userId 不匹配
       practiceApplyMapper.insert(cloneIgnoreId(dbPracticeApply, o -> o.setUserId(null)));
       // 测试 resume 不匹配
       practiceApplyMapper.insert(cloneIgnoreId(dbPracticeApply, o -> o.setResume(null)));
       // 测试 message 不匹配
       practiceApplyMapper.insert(cloneIgnoreId(dbPracticeApply, o -> o.setMessage(null)));
       // 测试 status 不匹配
       practiceApplyMapper.insert(cloneIgnoreId(dbPracticeApply, o -> o.setStatus(null)));
       // 测试 createTime 不匹配
       practiceApplyMapper.insert(cloneIgnoreId(dbPracticeApply, o -> o.setCreateTime(null)));
       // 测试 practiceId 不匹配
       practiceApplyMapper.insert(cloneIgnoreId(dbPracticeApply, o -> o.setPracticeId(null)));
       // 准备参数
       PracticeApplyExportReqVO reqVO = new PracticeApplyExportReqVO();
       reqVO.setUserId(null);
       reqVO.setResume(null);
       reqVO.setMessage(null);
       reqVO.setStatus(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setPracticeId(null);

       // 调用
       List<PracticeApplyDO> list = practiceApplyService.getPracticeApplyList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbPracticeApply, list.get(0));
    }

}
