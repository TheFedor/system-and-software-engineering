package com.misis.laba6.activator;

import com.misis.laba6.dto.HelloWorldDto;
import org.springframework.messaging.Message;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

@Component
public class HelloWorldActivator {

    @ServiceActivator(inputChannel = "helloWorldInboundChannel")
    public void activate(Message<HelloWorldDto> event) {
        System.out.println(event.getPayload().getMessageFullName() + " - " + event.getPayload().getMessageBirthday() + " " + event.getPayload().getRandomValue());
    }

}
