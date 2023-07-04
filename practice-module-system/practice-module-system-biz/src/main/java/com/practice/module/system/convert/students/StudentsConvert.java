package com.practice.module.system.convert.students;

import java.util.*;

import com.practice.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.practice.module.system.controller.admin.students.vo.*;
import com.practice.module.system.dal.dataobject.students.StudentsDO;

/**
 * 学生信息 Convert
 *
 * @author n3
 */
@Mapper
public interface StudentsConvert {

    StudentsConvert INSTANCE = Mappers.getMapper(StudentsConvert.class);

    StudentsDO convert(StudentCreateVO bean);

    StudentsDO convert(StudentsCreateReqVO bean);

    StudentsDO convert(StudentsUpdateReqVO bean);

    StudentsRespVO convert(StudentsDO bean);

    StudentRespVO convert2(StudentsDO bean);

    List<StudentsRespVO> convertList(List<StudentsDO> list);

    PageResult<StudentsRespVO> convertPage(PageResult<StudentsDO> page);

    List<StudentsExcelVO> convertList02(List<StudentsDO> list);

}
