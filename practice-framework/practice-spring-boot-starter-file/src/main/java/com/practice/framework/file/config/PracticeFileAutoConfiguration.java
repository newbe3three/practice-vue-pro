package com.practice.framework.file.config;

import com.practice.framework.file.core.client.FileClientFactory;
import com.practice.framework.file.core.client.FileClientFactoryImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 文件配置类
 *
 * @author 芋道源码
 */
@AutoConfiguration
public class PracticeFileAutoConfiguration {

    @Bean
    public FileClientFactory fileClientFactory() {
        return new FileClientFactoryImpl();
    }

}
