package com.maximorero.test_cache_vs_db.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // Ajustar el host/puerto si lo deseas de forma programática
        return new LettuceConnectionFactory("localhost", 6379);
    }

    @Bean
    public RedisTemplate<String, Boolean> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Boolean> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        // Para serializar las keys y values de forma simple (String -> Boolean)
        template.setKeySerializer(new StringRedisSerializer());
        // Podrías usar JdkSerializationRedisSerializer u otro para values
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }
}