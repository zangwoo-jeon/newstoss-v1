package com.newstoss.global.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.newstoss.global.kis.dto.KisApiRequestDto;
import com.newstoss.news.adapter.out.redis.subscriber.ChatRedisSubscriber;
import com.newstoss.news.adapter.out.redis.subscriber.NewsRedisSubscriber;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

import java.time.Duration;
import java.util.Map;

import com.newstoss.stock.application.V2.KisStreamListener;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class RedisConfig {

//    private final NewsRedisSubscriber subscriber;

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

    @Bean
    public ObjectMapper redisObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        objectMapper.activateDefaultTyping(
//                LaissezFaireSubTypeValidator.instance,
//                ObjectMapper.DefaultTyping.NON_FINAL,
//                JsonTypeInfo.As.PROPERTY
//        );
        return objectMapper;
    }

    @Bean
    @Primary
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory,
                                                       ObjectMapper redisObjectMapper) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        Jackson2JsonRedisSerializer<Object> serializer =
                new Jackson2JsonRedisSerializer<>(redisObjectMapper, Object.class);

        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);
        template.afterPropertiesSet();

        return template;
    }
//
//    @Bean(name = "listenerRedisTemplate")
//    public RedisTemplate<String, KisApiRequestDto> listenerRedisTemplate(RedisConnectionFactory connectionFactory) {
//        RedisTemplate<String, KisApiRequestDto> template = new RedisTemplate<>();
//        template.setConnectionFactory(connectionFactory);
//        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
//        return template;
//    }

//    @Bean
//    public StreamMessageListenerContainer<String, MapRecord<String,Object,Object>> streamMessageListenerContainer(
//            RedisConnectionFactory redisConnectionFactory,
//            KisStreamListener consumer // 등록할 핸들러 주입
//    ) {
//
//        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, MapRecord<String, Object, Object>> options =
//                StreamMessageListenerContainer.StreamMessageListenerContainerOptions.builder()
//                        .pollTimeout(Duration.ofSeconds(1))
//                        .batchSize(20)
//                        .keySerializer(new StringRedisSerializer())
//                        .hashKeySerializer(new StringRedisSerializer())
//                        .hashValueSerializer(new GenericJackson2JsonRedisSerializer()) // Jackson 기반 JSON 직렬화
//                        .build();
//
//        StreamMessageListenerContainer<String, MapRecord<String,Object,Object>> container =
//                StreamMessageListenerContainer.create(redisConnectionFactory, options);
//
//        container.receive(
//                Consumer.from("kis-group", "worker-1"),
//                StreamOffset.create("kis-api-request", ReadOffset.lastConsumed()),
//                consumer // KisApiStreamConsumer가 StreamListener 구현해야 함
//        );
//
//        container.start();
//        return container;
//    }
}