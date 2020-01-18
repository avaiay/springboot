package com.vv.study.listener;

import com.vv.study.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class CommonMqListener {

    private static final Logger log = LoggerFactory.getLogger(CommonMqListener.class);

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 监听消费用户日志
     *
     * @param message
     */
    @RabbitListener(queues = "${log.user.queue.name}", containerFactory = "singleListenerContainer")
    public void consumeUserLogQueue(@Payload byte[] message) {
        try {
            User user = objectMapper.readValue(message, User.class);
            log.info("监听消费用户日志 监听到消息： {} ", user.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

