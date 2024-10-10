package org.example.controller;

import org.example.entity.Message;
import org.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/")
public class MessageController {

    public final MessageService messageService;

    @Value("${spring.application.name}")
    private String appName;

    @Autowired
    public MessageController(MessageService messageService){
        this.messageService = messageService;
    }

    @GetMapping(path = "service-one/messages")
    public List<Message> getMessages(){
        return messageService.getMessages(appName);
    }

}
