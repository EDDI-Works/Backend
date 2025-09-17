package com.example.attack_on_monday_backend.redis_cache.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisCacheServiceImpl implements RedisCacheService {

    final private StringRedisTemplate redisTemplate;

    @Override
    public <K, V> void setKeyAndValue(K key, V value) {
        setKeyAndValue(key, value, Duration.ofMinutes(720));  // 기본 TTL 720분 적용
    }

    @Override
    public <K, V> void setKeyAndValue(K key, V value, Duration ttl) {
        String keyAsString = String.valueOf(key);
        String valueAsString = String.valueOf(value);

        try {
            ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
            valueOps.set(keyAsString, valueAsString, ttl);
            log.debug("캐시에 데이터 저장 성공 - key: {}, ttl: {}", keyAsString, ttl);
        } catch (Exception e) {
            log.error("Redis 캐시에 데이터 저장 실패 - key: {}, error: {}", keyAsString, e.getMessage(), e);
            throw new RuntimeException("캐시에 데이터를 저장할 수 없습니다.", e);
        }
    }
}
