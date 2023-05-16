package com.practice.module.system.mq.message.sensitiveword;

import com.practice.framework.mq.core.pubsub.AbstractChannelMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 敏感词的刷新 Message
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SensitiveWordRefreshMessage extends AbstractChannelMessage {

    @Override
    public String getChannel() {
        return "system.sensitive-word.refresh";
    }

}
