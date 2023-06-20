package com.practice.module.system.dal.mysql.practice;

import com.practice.framework.mybatis.core.mapper.BaseMapperX;
import com.practice.module.system.dal.dataobject.practice.PracticeRejectDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 实践驳回记录 Mapper
 *
 * @author n3
 */
@Mapper
public interface PracticeRejectMapper extends BaseMapperX<PracticeRejectDO> {
}
