package com.practice.framework.banner.config;

import com.practice.framework.banner.core.BannerApplicationRunner;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Banner 的自动配置类
 *
 * @author 芋道源码
 */
@AutoConfiguration
public class PracticeBannerAutoConfiguration {

    @Bean
    public BannerApplicationRunner bannerApplicationRunner() {
        return new BannerApplicationRunner();
    }

}
