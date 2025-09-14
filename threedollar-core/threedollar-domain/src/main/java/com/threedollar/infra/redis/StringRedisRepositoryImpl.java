package com.threedollar.infra.redis;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class StringRedisRepositoryImpl<K extends StringRedisKey<K, V>, V> implements StringRedisRepository<K, V> {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public V get(K k) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        return k.deserializeValue(operations.get(k.getKey()));
    }


    @Override
    public Map<K, V> getBulk(List<K> keys) {
        if (keys.isEmpty()) {
            return Collections.emptyMap();
        }
        K k = keys.get(0);

        ValueOperations<String, String> operations = redisTemplate.opsForValue();

        List<String> rawValues = operations.multiGet(keys.stream()
            .map(K::getKey)
            .collect(Collectors.toList())
        );

        if (rawValues == null) {
            return Collections.emptyMap();
        }

        List<V> values = rawValues.stream()
            .map(k::deserializeValue)
            .toList();

        Map<K, V> keyValues = new HashMap<>();
        for (int i = 0; i < keys.size(); i++) {
            keyValues.put(keys.get(i), values.get(i));
        }
        return keyValues;
    }


    @Override
    public void set(K k, V v) {
        setWithTtl(k, v, k.getTtl());

    }

    @Override
    public void setBulk(Map<K, V> keyValues) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        redisTemplate.executePipelined((RedisCallback<Object>) pipeline -> {
            keyValues.forEach((k, v) -> operations.set(k.getKey(), String.valueOf(v)));
            return null;
        });
    }

    @Override
    public void setWithTtl(K k, V v, Duration ttl) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        if (ttl == null) {
            operations.set(k.getKey(), k.serializeValue(v));
            return;
        }
        operations.set(k.getKey(), k.serializeValue(v), ttl.getSeconds(), TimeUnit.SECONDS);
    }

    @Override
    public void incrBulk(List<K> keys) {
        redisTemplate.executePipelined((RedisCallback<Object>) pipeline -> {
            keys.forEach(k -> pipeline.stringCommands().incr(k.getKey().getBytes(StandardCharsets.UTF_8)));
            return null;
        });
    }

    @Override
    public long incrBy(K k, long value) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        return Optional.ofNullable(operations.increment(k.getKey(), value)).orElse(0L);
    }



    @Override
    public void decrBulk(List<K> keys) {
        redisTemplate.executePipelined((RedisCallback<Object>) pipeline -> {
            keys.forEach(k -> pipeline.stringCommands().decr(k.getKey().getBytes(StandardCharsets.UTF_8)));
            return null;
        });
    }

    @Override
    public long decrBy(K k, long value) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        return Optional.ofNullable(operations.decrement(k.getKey(), value)).orElse(0L);
    }

    @Override
    public void del(K k) {
        redisTemplate.delete(k.getKey());
    }

    @Override
    public void delBulk(List<K> keys) {
        Set<String> keyStrings = keys.stream()
            .map(K::getKey)
            .collect(Collectors.toSet());
        redisTemplate.delete(keyStrings);
    }
}
