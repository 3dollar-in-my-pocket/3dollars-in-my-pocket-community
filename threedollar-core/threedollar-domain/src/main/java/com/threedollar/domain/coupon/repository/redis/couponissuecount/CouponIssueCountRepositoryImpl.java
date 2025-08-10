package com.threedollar.domain.coupon.repository.redis.couponissuecount;

import com.threedollar.infra.redis.StringRedisRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CouponIssueCountRepositoryImpl implements CouponIssueCountRepository {

    private final StringRedisRepository<CouponIssueCountKey, Long> stringRedisRepository;

    @Override
    public void incrByCount(Long couponId, String workspaceId, String providerId) {
        CouponIssueCountKey key = CouponIssueCountKey.builder()
            .couponId(couponId)
            .workspaceId(workspaceId)
            .providerId(providerId)
            .build();
        stringRedisRepository.incr(key);
    }

    @Override
    public Long getValueByKey(CouponIssueCountKey key) {
        return stringRedisRepository.get(key);
    }
}
