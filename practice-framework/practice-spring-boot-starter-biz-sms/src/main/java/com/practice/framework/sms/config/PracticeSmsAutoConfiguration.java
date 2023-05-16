package com.practice.framework.sms.config;

import com.practice.framework.sms.core.client.SmsClientFactory;
import com.practice.framework.sms.core.client.impl.SmsClientFactoryImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 短信配置类
 *
 * @author 芋道源码
 */
@AutoConfiguration
public class PracticeSmsAutoConfiguration {

    @Bean
    public SmsClientFactory smsClientFactory() {
        return new SmsClientFactoryImpl();
    }

}
