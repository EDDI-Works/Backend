package com.example.attack_on_monday_backend.redis_cache.service;

import java.time.Duration;

public interface RedisCacheService {
    <K, V> void setKeyAndValue(K key, V value);
    <K, V> void setKeyAndValue(K key, V value, Duration ttl);
}
