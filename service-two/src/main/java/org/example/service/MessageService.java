package org.example.service;

import org.example.entity.Message;
import org.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final RabbitMessageSender rabbitMessageSender;

    @Value("${spring.application.name}")
    private String appName;

    @Autowired
    public MessageService(MessageRepository messageRepository,
                        RabbitMessageSender rabbitMessageSender){
        this.messageRepository = messageRepository;
        this.rabbitMessageSender = rabbitMessageSender;
    }

    public List<Message> getMessages(String appName) {
        return messageRepository.findByName(appName);
    }

    public void addMessage(Message message) {
        messageRepository.save(message);
    }
    public void sendMessage(Message message) {
        rabbitMessageSender.send(message);
        addMessage(message);
    }

    @Scheduled(fixedDelay = 60000, initialDelay = 30000)
    public void generateMessage(){
        String value = UUID.randomUUID().toString();
        Message toSend = new Message(appName, value, Date.from(Instant.now()));
        sendMessage(toSend);
    }
}
