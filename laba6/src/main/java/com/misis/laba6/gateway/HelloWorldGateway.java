package com.misis.laba6.gateway;

import com.misis.laba6.dto.HelloWorldDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
public class HelloWorldGateway {

    private final MessageChannel messageChannel;

    @Autowired
    public HelloWorldGateway(
            @Qualifier("helloWorldOutboundChannel") MessageChannel messageChannel
    ) {
        this.messageChannel = messageChannel;
    }

    public void send(HelloWorldDto event)
    {
        messageChannel.send(MessageBuilder.withPayload(event).build());
    }

}
