package sensebreak.notificationservice.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;
import sensebreak.notificationservice.model.NotificationMessage;

/**
 * Configuration class for Redis integration in the Notification Service.
 * Defines a RedisTemplate for storing and retrieving {@link NotificationMessage} objects.
 */
@Configuration
public class RedisConfig {

    /**
     * Configures a {@link RedisTemplate} for handling {@link NotificationMessage} objects as values.
     *
     * @param factory the Redis connection factory
     * @return a configured RedisTemplate
     */
    @Bean
    public RedisTemplate<String, NotificationMessage> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, NotificationMessage> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.activateDefaultTyping(
                BasicPolymorphicTypeValidator.builder().allowIfSubType(Object.class).build(),
                ObjectMapper.DefaultTyping.NON_FINAL
        );
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Jackson2JsonRedisSerializer<NotificationMessage> serializer =
                new Jackson2JsonRedisSerializer<>(NotificationMessage.class);
        serializer.setObjectMapper(mapper);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);

        return template;
    }
}
