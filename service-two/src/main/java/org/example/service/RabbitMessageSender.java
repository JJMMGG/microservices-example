package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class RabbitMessageSender {

    private static final Logger log = LoggerFactory.getLogger(
            MessageService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final String key="service.two.warning";

    @Autowired
    private TopicExchange topicExchange;

    public void send(Message message){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a z");
            objectMapper.setDateFormat(df);
            String jsonMessage =  objectMapper.writeValueAsString(message);
            rabbitTemplate.convertAndSend(topicExchange.getName(),key,
                    jsonMessage);
            log.info("Sending message "+ jsonMessage +" to "+ topicExchange);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
