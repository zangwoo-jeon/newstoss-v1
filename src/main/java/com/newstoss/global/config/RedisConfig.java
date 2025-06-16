package com.newstoss.global.config;

import com.newstoss.news.adapter.out.redis.chatbot.ChatRedisSubscriber;
import com.newstoss.news.adapter.out.redis.highlight.NewsRedisSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    private final NewsRedisSubscriber subscriber;

    @Bean
    public RedisMessageListenerContainer redisContainer(
            RedisConnectionFactory connectionFactory,
            MessageListenerAdapter newsAdapter,
            MessageListenerAdapter chatAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(newsAdapter, new ChannelTopic("news-channel"));
        container.addMessageListener(chatAdapter, new ChannelTopic("chat-response"));
        return container;
    }
    @Bean
    public MessageListenerAdapter newsAdapter(NewsRedisSubscriber subscriber) {
        return new MessageListenerAdapter(subscriber, "onMessage");
    }

    @Bean
    public MessageListenerAdapter chatAdapter(ChatRedisSubscriber subscriber) {
        return new MessageListenerAdapter(subscriber, "onMessage");
    }
}