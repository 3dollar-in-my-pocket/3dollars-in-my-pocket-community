package com.threedollar.domain.coupon.repository;

import com.threedollar.infra.redis.StringRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CouponUseCountRepository {

    private final StringRedisRepository<CouponUseCountKey, Long> stringRedisRepository;

    public Long incr(CouponUseCountKey key) {
        return stringRedisRepository.incr(key);
    }

    public Long decr(CouponUseCountKey key) {
        return stringRedisRepository.decr(key);
    }

    public Long get(CouponUseCountKey key) {
        return stringRedisRepository.get(key);
    }

}
