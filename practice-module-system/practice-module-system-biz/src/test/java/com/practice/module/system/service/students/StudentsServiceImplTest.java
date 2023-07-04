package com.practice.module.system.service.students;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import com.practice.framework.test.core.ut.BaseDbUnitTest;

import com.practice.module.system.controller.admin.students.vo.*;
import com.practice.module.system.dal.dataobject.students.StudentsDO;
import com.practice.module.system.dal.mysql.students.StudentsMapper;
import com.practice.framework.common.pojo.PageResult;

import javax.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;
import java.time.LocalDateTime;

import static cn.hutool.core.util.RandomUtil.*;
import static com.practice.module.system.enums.ErrorCodeConstants.*;
import static com.practice.framework.test.core.util.AssertUtils.*;
import static com.practice.framework.test.core.util.RandomUtils.*;
import static com.practice.framework.common.util.date.LocalDateTimeUtils.*;
import static com.practice.framework.common.util.object.ObjectUtils.*;
import static com.practice.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
* {@link StudentsServiceImpl} 的单元测试类
*
* @author n3
*/
@Import(StudentsServiceImpl.class)
public class StudentsServiceImplTest extends BaseDbUnitTest {

    @Resource
    private StudentsServiceImpl studentsService;

    @Resource
    private StudentsMapper studentsMapper;

    @Test
    public void testCreateStudents_success() {
        // 准备参数
        StudentsCreateReqVO reqVO = randomPojo(StudentsCreateReqVO.class);

        // 调用
        Long studentsId = studentsService.createStudents(reqVO);
        // 断言
        assertNotNull(studentsId);
        // 校验记录的属性是否正确
        StudentsDO students = studentsMapper.selectById(studentsId);
        assertPojoEquals(reqVO, students);
    }

    @Test
    public void testUpdateStudents_success() {
        // mock 数据
        StudentsDO dbStudents = randomPojo(StudentsDO.class);
        studentsMapper.insert(dbStudents);// @Sql: 先插入出一条存在的数据
        // 准备参数
        StudentsUpdateReqVO reqVO = randomPojo(StudentsUpdateReqVO.class, o -> {
            o.setId(dbStudents.getId()); // 设置更新的 ID
        });

        // 调用
        studentsService.updateStudents(reqVO);
        // 校验是否更新正确
        StudentsDO students = studentsMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, students);
    }

    @Test
    public void testUpdateStudents_notExists() {
        // 准备参数
        StudentsUpdateReqVO reqVO = randomPojo(StudentsUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> studentsService.updateStudents(reqVO), STUDENTS_NOT_EXISTS);
    }

    @Test
    public void testDeleteStudents_success() {
        // mock 数据
        StudentsDO dbStudents = randomPojo(StudentsDO.class);
        studentsMapper.insert(dbStudents);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbStudents.getId();

        // 调用
        studentsService.deleteStudents(id);
       // 校验数据不存在了
       assertNull(studentsMapper.selectById(id));
    }

    @Test
    public void testDeleteStudents_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> studentsService.deleteStudents(id), STUDENTS_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetStudentsPage() {
       // mock 数据
       StudentsDO dbStudents = randomPojo(StudentsDO.class, o -> { // 等会查询到
           o.setUserId(null);
           o.setName(null);
           o.setDeptId(null);
           o.setTeacherId(null);
           o.setMajor(null);
           o.setMobile(null);
           o.setCardId(null);
           o.setCreateTime(null);
       });
       studentsMapper.insert(dbStudents);
       // 测试 userId 不匹配
       studentsMapper.insert(cloneIgnoreId(dbStudents, o -> o.setUserId(null)));
       // 测试 name 不匹配
       studentsMapper.insert(cloneIgnoreId(dbStudents, o -> o.setName(null)));
       // 测试 deptId 不匹配
       studentsMapper.insert(cloneIgnoreId(dbStudents, o -> o.setDeptId(null)));
       // 测试 teacherId 不匹配
       studentsMapper.insert(cloneIgnoreId(dbStudents, o -> o.setTeacherId(null)));
       // 测试 major 不匹配
       studentsMapper.insert(cloneIgnoreId(dbStudents, o -> o.setMajor(null)));
       // 测试 mobile 不匹配
       studentsMapper.insert(cloneIgnoreId(dbStudents, o -> o.setMobile(null)));
       // 测试 cardId 不匹配
       studentsMapper.insert(cloneIgnoreId(dbStudents, o -> o.setCardId(null)));
       // 测试 createTime 不匹配
       studentsMapper.insert(cloneIgnoreId(dbStudents, o -> o.setCreateTime(null)));
       // 准备参数
       StudentsPageReqVO reqVO = new StudentsPageReqVO();
       reqVO.setUserId(null);
       reqVO.setName(null);
       reqVO.setDeptId(null);
       reqVO.setTeacherId(null);
       reqVO.setMajor(null);
       reqVO.setMobile(null);
       reqVO.setCardId(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<StudentsDO> pageResult = studentsService.getStudentsPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbStudents, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetStudentsList() {
       // mock 数据
       StudentsDO dbStudents = randomPojo(StudentsDO.class, o -> { // 等会查询到
           o.setUserId(null);
           o.setName(null);
           o.setDeptId(null);
           o.setTeacherId(null);
           o.setMajor(null);
           o.setMobile(null);
           o.setCardId(null);
           o.setCreateTime(null);
       });
       studentsMapper.insert(dbStudents);
       // 测试 userId 不匹配
       studentsMapper.insert(cloneIgnoreId(dbStudents, o -> o.setUserId(null)));
       // 测试 name 不匹配
       studentsMapper.insert(cloneIgnoreId(dbStudents, o -> o.setName(null)));
       // 测试 deptId 不匹配
       studentsMapper.insert(cloneIgnoreId(dbStudents, o -> o.setDeptId(null)));
       // 测试 teacherId 不匹配
       studentsMapper.insert(cloneIgnoreId(dbStudents, o -> o.setTeacherId(null)));
       // 测试 major 不匹配
       studentsMapper.insert(cloneIgnoreId(dbStudents, o -> o.setMajor(null)));
       // 测试 mobile 不匹配
       studentsMapper.insert(cloneIgnoreId(dbStudents, o -> o.setMobile(null)));
       // 测试 cardId 不匹配
       studentsMapper.insert(cloneIgnoreId(dbStudents, o -> o.setCardId(null)));
       // 测试 createTime 不匹配
       studentsMapper.insert(cloneIgnoreId(dbStudents, o -> o.setCreateTime(null)));
       // 准备参数
       StudentsExportReqVO reqVO = new StudentsExportReqVO();
       reqVO.setUserId(null);
       reqVO.setName(null);
       reqVO.setDeptId(null);
       reqVO.setTeacherId(null);
       reqVO.setMajor(null);
       reqVO.setMobile(null);
       reqVO.setCardId(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<StudentsDO> list = studentsService.getStudentsList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbStudents, list.get(0));
    }

}
