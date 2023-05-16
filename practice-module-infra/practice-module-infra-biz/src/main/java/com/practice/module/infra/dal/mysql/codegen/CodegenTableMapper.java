package com.practice.module.infra.dal.mysql.codegen;

import com.practice.framework.common.pojo.PageResult;
import com.practice.framework.mybatis.core.mapper.BaseMapperX;
import com.practice.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.practice.module.infra.controller.admin.codegen.vo.table.CodegenTablePageReqVO;
import com.practice.module.infra.dal.dataobject.codegen.CodegenTableDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CodegenTableMapper extends BaseMapperX<CodegenTableDO> {

    default CodegenTableDO selectByTableNameAndDataSourceConfigId(String tableName, Long dataSourceConfigId) {
        return selectOne(CodegenTableDO::getTableName, tableName,
                CodegenTableDO::getDataSourceConfigId, dataSourceConfigId);
    }

    default PageResult<CodegenTableDO> selectPage(CodegenTablePageReqVO pageReqVO) {
        return selectPage(pageReqVO, new LambdaQueryWrapperX<CodegenTableDO>()
                .likeIfPresent(CodegenTableDO::getTableName, pageReqVO.getTableName())
                .likeIfPresent(CodegenTableDO::getTableComment, pageReqVO.getTableComment())
                .likeIfPresent(CodegenTableDO::getClassName, pageReqVO.getClassName())
                .betweenIfPresent(CodegenTableDO::getCreateTime, pageReqVO.getCreateTime()));
    }

    default List<CodegenTableDO> selectListByDataSourceConfigId(Long dataSourceConfigId) {
        return selectList(CodegenTableDO::getDataSourceConfigId, dataSourceConfigId);
    }

}
