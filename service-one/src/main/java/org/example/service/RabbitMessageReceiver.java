package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class RabbitMessageReceiver {

    private static final Logger log = LoggerFactory.getLogger(
                                        RabbitMessageReceiver.class);
    @Autowired
    public MessageService messageService;

    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void receive(String in) throws InterruptedException,
                                            JsonProcessingException {
        log.info("Received '" + in + "'");
        StopWatch watch = new StopWatch();
        watch.start();

        ObjectMapper objectMapper = new ObjectMapper();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        objectMapper.setDateFormat(df);
        Message message = objectMapper.readValue(in, Message.class);

        messageService.addMessage(message);
        watch.stop();
        log.info("Process data done in " + watch.getTotalTimeSeconds() + "s");
    }
}
