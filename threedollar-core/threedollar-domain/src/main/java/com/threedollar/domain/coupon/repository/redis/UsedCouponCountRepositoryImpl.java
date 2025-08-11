package com.threedollar.domain.coupon.repository.redis;

import com.threedollar.infra.redis.StringRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UsedCouponCountRepositoryImpl implements UsedCouponCountRepository {

    private final StringRedisRepository<UsedCouponCountKey, Long> stringRedisRepository;

    @Override
    public void incrByCount(Long couponId, String workspaceId, String providerId) {
        UsedCouponCountKey key = UsedCouponCountKey.builder()
            .couponId(couponId)
            .workspaceId(workspaceId)
            .providerId(providerId)
            .build();
        stringRedisRepository.incr(key);
    }

    @Override
    public Long getValueByKey(UsedCouponCountKey key) {
        return stringRedisRepository.get(key);
    }
}
