package org.example.config.queue;

import org.example.service.RabbitMessageReceiver;
import org.example.service.RabbitMessageSender;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    private final static String exchangeName = "my.services.exchange";
    private final static String routingKey = "service.two.*";

    @Bean()
    public TopicExchange topic(){
        return new TopicExchange(exchangeName);
    }

    //Reciever config
    private static class ReceiverConfig {

        @Bean
        public RabbitMessageReceiver receiver() {
            return new RabbitMessageReceiver();
        }

        @Bean
        public Queue autoDeleteQueue1() {
            return new AnonymousQueue();
        }

        @Bean
        public Binding binding1a(TopicExchange topic,
                                 Queue autoDeleteQueue1) {
            return BindingBuilder.bind(autoDeleteQueue1)
                    .to(topic)
                    .with(routingKey);
        }
    }

    //Sender config
    @Bean
    public RabbitMessageSender sender() {
        return new RabbitMessageSender();
    }
}
