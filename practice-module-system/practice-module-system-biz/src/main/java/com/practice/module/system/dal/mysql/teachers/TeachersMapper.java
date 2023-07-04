package com.practice.module.system.dal.mysql.teachers;

import java.util.*;

import com.practice.framework.common.pojo.PageResult;
import com.practice.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.practice.framework.mybatis.core.mapper.BaseMapperX;
import com.practice.module.system.dal.dataobject.teachers.TeachersDO;
import org.apache.ibatis.annotations.Mapper;
import com.practice.module.system.controller.admin.teachers.vo.*;

/**
 * 导师信息 Mapper
 *
 * @author n3
 */
@Mapper
public interface TeachersMapper extends BaseMapperX<TeachersDO> {

    default PageResult<TeachersDO> selectPage(TeachersPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TeachersDO>()
                .eqIfPresent(TeachersDO::getUserId, reqVO.getUserId())
                .likeIfPresent(TeachersDO::getName, reqVO.getName())
                .eqIfPresent(TeachersDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(TeachersDO::getMajor, reqVO.getMajor())
                .eqIfPresent(TeachersDO::getMobile, reqVO.getMobile())
                .eqIfPresent(TeachersDO::getCardId, reqVO.getCardId())
                .betweenIfPresent(TeachersDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TeachersDO::getId));
    }

    default List<TeachersDO> selectList(TeachersExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<TeachersDO>()
                .eqIfPresent(TeachersDO::getUserId, reqVO.getUserId())
                .likeIfPresent(TeachersDO::getName, reqVO.getName())
                .eqIfPresent(TeachersDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(TeachersDO::getMajor, reqVO.getMajor())
                .eqIfPresent(TeachersDO::getMobile, reqVO.getMobile())
                .eqIfPresent(TeachersDO::getCardId, reqVO.getCardId())
                .betweenIfPresent(TeachersDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TeachersDO::getId));
    }

}
