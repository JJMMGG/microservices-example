package org.example.config.queue;

import org.example.service.RabbitMessageReceiver;
import org.example.service.RabbitMessageSender;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {
    private static final String exchangeName = "my.services.exchange";
    private static final String routingKey = "service.one.*";

    @Bean
    public TopicExchange topic(){
        return new TopicExchange(exchangeName);
    }

    public static class ReceiverConfig{

        @Bean
        public RabbitMessageReceiver receiver(){
            return new RabbitMessageReceiver();
        }

        @Bean
        public Queue autoDeleteQueue(){
            return new AnonymousQueue();
        }

        @Bean
        public Binding binding(TopicExchange topic, Queue autoDeleteQueue){
            return BindingBuilder.bind(autoDeleteQueue)
                    .to(topic)
                    .with(routingKey);
        }
    }

    @Bean
    public RabbitMessageSender sender(){
        return new RabbitMessageSender();
    }
}
