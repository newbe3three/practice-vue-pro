package com.practice.module.system.mq.consumer.sensitiveword;

import com.practice.framework.mq.core.pubsub.AbstractChannelMessageListener;
import com.practice.module.system.mq.message.sensitiveword.SensitiveWordRefreshMessage;
import com.practice.module.system.service.sensitiveword.SensitiveWordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 针对 {@link SensitiveWordRefreshMessage} 的消费者
 *
 * @author 芋道源码
 */
@Component
@Slf4j
public class SensitiveWordRefreshConsumer extends AbstractChannelMessageListener<SensitiveWordRefreshMessage> {

    @Resource
    private SensitiveWordService sensitiveWordService;

    @Override
    public void onMessage(SensitiveWordRefreshMessage message) {
        log.info("[onMessage][收到 SensitiveWord 刷新消息]");
        sensitiveWordService.initLocalCache();
    }

}
