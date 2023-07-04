package com.practice.module.system.convert.teachers;

import java.util.*;

import com.practice.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.practice.module.system.controller.admin.teachers.vo.*;
import com.practice.module.system.dal.dataobject.teachers.TeachersDO;

/**
 * 导师信息 Convert
 *
 * @author n3
 */
@Mapper
public interface TeachersConvert {

    TeachersConvert INSTANCE = Mappers.getMapper(TeachersConvert.class);

    TeachersDO convert(TeachersCreateReqVO bean);
    TeachersDO convert(TeacherCreateVO bean);
    TeachersDO convert(TeachersUpdateReqVO bean);

    TeachersRespVO convert(TeachersDO bean);

    TeacherRespVO convert2(TeachersDO bean);

    List<TeachersRespVO> convertList(List<TeachersDO> list);

    PageResult<TeachersRespVO> convertPage(PageResult<TeachersDO> page);

    List<TeachersExcelVO> convertList02(List<TeachersDO> list);

}
