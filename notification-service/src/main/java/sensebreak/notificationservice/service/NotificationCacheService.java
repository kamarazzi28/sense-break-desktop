package sensebreak.notificationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import sensebreak.notificationservice.model.NotificationMessage;

import java.time.Duration;

/**
 * Service responsible for caching notification messages in Redis.
 */
@Service
@RequiredArgsConstructor
public class NotificationCacheService {

    private final RedisTemplate<String, NotificationMessage> redisTemplate;

    private static final String CACHE_KEY_PREFIX = "notification:";

    /**
     * Caches a notification message in Redis with a 30-minute expiration time.
     *
     * @param message the notification message to be cached
     */
    public void cacheNotification(NotificationMessage message) {
        String key = CACHE_KEY_PREFIX + message.getUserId();
        redisTemplate.opsForValue().set(key, message, Duration.ofMinutes(30));
        System.out.println("Cached notification for user: " + message.getUserId());
    }
}
