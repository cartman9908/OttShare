package seohan.ottshare.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenBlackListService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final String BLACKLIST_PREFIX = "blacklist";

    public void addToBlackList(String token, long expirationTime) {
        redisTemplate.opsForValue().set(BLACKLIST_PREFIX + token, true, expirationTime, TimeUnit.MILLISECONDS);
    }

    public boolean isBlackList(String token) {
        Boolean isBlackListed = (Boolean) redisTemplate.opsForValue().get(BLACKLIST_PREFIX + token);
        return isBlackListed != null && isBlackListed;
    }
}