package com.practice.module.system.dal.mysql.students;

import java.util.*;

import com.practice.framework.common.pojo.PageResult;
import com.practice.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.practice.framework.mybatis.core.mapper.BaseMapperX;
import com.practice.module.system.dal.dataobject.students.StudentsDO;
import org.apache.ibatis.annotations.Mapper;
import com.practice.module.system.controller.admin.students.vo.*;

/**
 * 学生信息 Mapper
 *
 * @author n3
 */
@Mapper
public interface StudentsMapper extends BaseMapperX<StudentsDO> {

    default PageResult<StudentsDO> selectPage(StudentsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<StudentsDO>()
                .eqIfPresent(StudentsDO::getUserId, reqVO.getUserId())
                .likeIfPresent(StudentsDO::getName, reqVO.getName())
                .eqIfPresent(StudentsDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(StudentsDO::getTeacherId, reqVO.getTeacherId())
                .eqIfPresent(StudentsDO::getMajor, reqVO.getMajor())
                .eqIfPresent(StudentsDO::getMobile, reqVO.getMobile())
                .eqIfPresent(StudentsDO::getCardId, reqVO.getCardId())
                .betweenIfPresent(StudentsDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(StudentsDO::getId));
    }

    default List<StudentsDO> selectList(StudentsExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<StudentsDO>()
                .eqIfPresent(StudentsDO::getUserId, reqVO.getUserId())
                .likeIfPresent(StudentsDO::getName, reqVO.getName())
                .eqIfPresent(StudentsDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(StudentsDO::getTeacherId, reqVO.getTeacherId())
                .eqIfPresent(StudentsDO::getMajor, reqVO.getMajor())
                .eqIfPresent(StudentsDO::getMobile, reqVO.getMobile())
                .eqIfPresent(StudentsDO::getCardId, reqVO.getCardId())
                .betweenIfPresent(StudentsDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(StudentsDO::getId));
    }

}
