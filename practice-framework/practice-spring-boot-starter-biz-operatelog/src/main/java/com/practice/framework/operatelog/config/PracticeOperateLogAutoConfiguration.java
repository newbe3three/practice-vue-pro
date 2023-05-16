package com.practice.framework.operatelog.config;

import com.practice.framework.operatelog.core.aop.OperateLogAspect;
import com.practice.framework.operatelog.core.service.OperateLogFrameworkService;
import com.practice.framework.operatelog.core.service.OperateLogFrameworkServiceImpl;
import com.practice.module.system.api.logger.OperateLogApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class PracticeOperateLogAutoConfiguration {

    @Bean
    public OperateLogAspect operateLogAspect() {
        return new OperateLogAspect();
    }

    @Bean
    public OperateLogFrameworkService operateLogFrameworkService(OperateLogApi operateLogApi) {
        return new OperateLogFrameworkServiceImpl(operateLogApi);
    }

}
