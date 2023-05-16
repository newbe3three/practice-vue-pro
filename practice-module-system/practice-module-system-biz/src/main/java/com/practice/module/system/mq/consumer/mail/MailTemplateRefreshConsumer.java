package com.practice.module.system.mq.consumer.mail;

import com.practice.framework.mq.core.pubsub.AbstractChannelMessageListener;
import com.practice.module.system.mq.message.mail.MailTemplateRefreshMessage;
import com.practice.module.system.service.mail.MailTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 针对 {@link MailTemplateRefreshMessage} 的消费者
 *
 * @author wangjingyi
 */
@Component
@Slf4j
public class MailTemplateRefreshConsumer extends AbstractChannelMessageListener<MailTemplateRefreshMessage> {

    @Resource
    private MailTemplateService mailTemplateService;

    @Override
    public void onMessage(MailTemplateRefreshMessage message) {
        log.info("[onMessage][收到 Mail Template 刷新信息]");
        mailTemplateService.initLocalCache();
    }

}
