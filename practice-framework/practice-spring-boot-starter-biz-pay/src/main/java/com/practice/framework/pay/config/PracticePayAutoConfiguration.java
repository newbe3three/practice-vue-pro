package com.practice.framework.pay.config;

import com.practice.framework.pay.core.client.PayClientFactory;
import com.practice.framework.pay.core.client.impl.PayClientFactoryImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 支付配置类
 *
 * @author 芋道源码
 */
@AutoConfiguration
@EnableConfigurationProperties(PayProperties.class)
public class PracticePayAutoConfiguration {

    @Bean
    public PayClientFactory payClientFactory() {
        return new PayClientFactoryImpl();
    }

}
