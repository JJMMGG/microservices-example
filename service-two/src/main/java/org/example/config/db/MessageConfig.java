package org.example.config.db;

import org.example.entity.Message;
import org.example.repository.MessageRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Configuration
public class MessageConfig {

    @Bean
    CommandLineRunner commandLineRunner(MessageRepository messageRepository) {
        return args -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Message message1 = new Message("service-two",
                    "u1o239js12",
                    Date.from(Instant.now()));
            Message message2 = new Message("service-one",
                    "u1o2dg9g12",
                    Date.from(Instant.now()));
            messageRepository.saveAll(List.of(message1, message2));
        };
    }
}
