package com.example.rabitdemo;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RabitDemoApplication {

    static final String rabbitHost = "localhost";
    static final String rabbitUser= "guest";
    static final String rabbitPassword = "guest";

    static final String exchangeName = "FedorK-BIVT-20-4-exchange-direct";
    static final String queueName1 = "FedorK-BIVT-20-4-direct-q1";
    static final String queueName2 = "FedorK-BIVT-20-4-direct-q2";

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(rabbitHost);
        connectionFactory.setUsername(rabbitUser);
        connectionFactory.setPassword(rabbitPassword);
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }

    @Bean
    Queue queue1() {
        return new Queue(queueName1, false);
    }

    @Bean
    Queue queue2() {
        return new Queue(queueName2, false);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    Binding binding1(Queue queue1, DirectExchange exchange) {
        return BindingBuilder.bind(queue1).to(exchange).with("key1");
    }

    @Bean
    Binding binding2(Queue queue2, DirectExchange exchange) {
        return BindingBuilder.bind(queue2).to(exchange).with("key2");
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName1, queueName2);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    public static void main(String[] args) {
        SpringApplication.run(RabitDemoApplication.class, args);
    }

}
