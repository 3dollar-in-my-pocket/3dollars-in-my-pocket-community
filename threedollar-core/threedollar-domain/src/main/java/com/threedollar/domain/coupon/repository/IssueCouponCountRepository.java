package com.threedollar.domain.coupon.repository;

import com.threedollar.domain.coupon.IssueCouponCountKey;
import com.threedollar.infra.redis.StringRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class IssueCouponCountRepository {

    private final StringRedisRepository<IssueCouponCountKey, Long> stringRedisRepository;

    public Long incr(IssueCouponCountKey key) {
        return stringRedisRepository.incr(key);
    }

    public Long decr(IssueCouponCountKey key) {
        return stringRedisRepository.decr(key);
    }

    public Long get(IssueCouponCountKey key) {
        return stringRedisRepository.get(key);
    }

}
