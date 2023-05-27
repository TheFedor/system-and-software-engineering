package com.misis.laba6.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import com.misis.laba6.dto.HelloWorldDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.core.MessageSelector;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.redis.inbound.RedisQueueMessageDrivenEndpoint;
import org.springframework.integration.redis.outbound.RedisQueueOutboundChannelAdapter;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

@Configuration
@EnableIntegration
public class RedisIntegrationConfiguration {

    private static final String QUEUE_HELLO_WORLD = "queue:hello-world";

    @Bean("helloWorldInboundChannelFlow")
    public IntegrationFlow redisHelloWorldEventInboundChannelFlow(
            RedisConnectionFactory redisConnectionFactory,
            @Qualifier("helloWorldInboundChannel") MessageChannel channel
    ) {
        RedisQueueMessageDrivenEndpoint endpoint =
                new RedisQueueMessageDrivenEndpoint(QUEUE_HELLO_WORLD, redisConnectionFactory);
        Jackson2JsonRedisSerializer<HelloWorldDto> serializer
                = new Jackson2JsonRedisSerializer<>(HelloWorldDto.class);

        endpoint.setSerializer(serializer);
        endpoint.setBeanName("helloWorldRedisQueueMessageDrivenEndpoint");

        return IntegrationFlows
                .from(endpoint)
                .filter(filter())
                .channel(channel)
                .get();
    }

    @Bean("helloWorldOutboundChannelFlow")
    public IntegrationFlow redisHelloWorldEventOutboundChannelFlow(
            RedisConnectionFactory redisConnectionFactory,
            @Qualifier("helloWorldOutboundChannel") MessageChannel channel
    ) {
        Jackson2JsonRedisSerializer<HelloWorldDto> serializer
                = new Jackson2JsonRedisSerializer<>(HelloWorldDto.class);

        RedisQueueOutboundChannelAdapter channelAdapter =
                new RedisQueueOutboundChannelAdapter(QUEUE_HELLO_WORLD, redisConnectionFactory);
        channelAdapter.setSerializer(serializer);

        return IntegrationFlows
                .from(channel)
                .handle(channelAdapter)
                .get();
    }
    /*
    @Bean("helloWorldOutboundChannel")
    public SubscribableChannel logEventOutboundChannel() {
        PublishSubscribeChannel channel = new PublishSubscribeChannel();
        channel.setMaxSubscribers(3);
        channel.setComponentName("helloWorldOutboundChannel");

        return channel;
    }

    @Bean("helloWorldInboundChannel")
    public SubscribableChannel logEventInboundChannel() {
        PublishSubscribeChannel channel = new PublishSubscribeChannel();
        channel.setMaxSubscribers(3);
        channel.setComponentName("helloWorldInboundChannel");
        return channel;
    }*/

    @Bean("helloWorldOutboundChannel")
    public MessageChannel logEventOutboundChannel() {
        return new DirectChannel();
    }

    @Bean("helloWorldInboundChannel")
    public MessageChannel logEventInboundChannel() {
        return new DirectChannel();
    }


    @Bean
    @Filter(inputChannel = "helloWorldInboundChannel", outputChannel = "helloWorldOutboundChannel")
    public MessageSelector filter()
    {
        return message -> {
            HelloWorldDto dto = (HelloWorldDto) message.getPayload();
            int randomValue = dto.getRandomValue();
            return randomValue % 2 != 0;
        };
    }


}
