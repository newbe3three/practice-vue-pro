package com.practice.module.pay.dal.mysql.demo;

import com.practice.framework.common.pojo.PageParam;
import com.practice.framework.common.pojo.PageResult;
import com.practice.framework.mybatis.core.mapper.BaseMapperX;
import com.practice.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.practice.module.pay.dal.dataobject.demo.PayDemoOrderDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 示例订单 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface PayDemoOrderMapper extends BaseMapperX<PayDemoOrderDO> {

    default PageResult<PayDemoOrderDO> selectPage(PageParam reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PayDemoOrderDO>()
                .orderByDesc(PayDemoOrderDO::getId));
    }

    default int updateByIdAndPayed(Long id, boolean wherePayed, PayDemoOrderDO updateObj) {
        return update(updateObj, new LambdaQueryWrapperX<PayDemoOrderDO>()
                .eq(PayDemoOrderDO::getId, id).eq(PayDemoOrderDO::getPayed, wherePayed));
    }

}
