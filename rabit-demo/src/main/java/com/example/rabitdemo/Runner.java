package com.example.rabitdemo;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner{

    private final RabbitTemplate rabbitTemplate;

    public Runner(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        while(true) {
            System.out.println("Sending messages...");
            rabbitTemplate.convertAndSend(RabitDemoApplication.exchangeName, "key1", "Hi task 2 q1");
            Thread.sleep(2000);
            rabbitTemplate.convertAndSend(RabitDemoApplication.exchangeName, "key2", "Hi task 2 q2");
            Thread.sleep(1000);
        }
    }

}
