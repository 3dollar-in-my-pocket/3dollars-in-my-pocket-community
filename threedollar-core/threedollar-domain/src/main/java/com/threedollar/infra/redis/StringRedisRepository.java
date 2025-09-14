package com.threedollar.infra.redis;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public interface StringRedisRepository<K extends StringRedisKey<K, V>, V> {

    V get(K k);

    Map<K, V> getBulk(List<K> keys);

    void set(K k, V v);

    void setBulk(Map<K, V> keyValues);

    void setWithTtl(K k, V v, Duration ttl);

    default long incr(K k) {
        return incrBy(k, 1);
    }

    void incrBulk(List<K> keys);

    long incrBy(K k, long value);

    default long decr(K k) {
        return decrBy(k, 1);
    };

    void decrBulk(List<K> keys);

    long decrBy(K k, long value);

    void del(K k);

    void delBulk(List<K> keys);
}
