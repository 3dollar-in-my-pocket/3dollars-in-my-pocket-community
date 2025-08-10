package com.threedollar.domain.coupon.repository.redis.couponissuecount;

import com.threedollar.infra.redis.StringRedisKey;

import java.time.Duration;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CouponIssueCountKey implements StringRedisKey<CouponIssueCountKey, Long> {

    private static final long DEFAULT_VALUE = 0L;

    private final Long couponId;

    private final String workspaceId;

    private final String providerId;

    @Builder
    public CouponIssueCountKey(Long couponId, String workspaceId, String providerId) {
        this.couponId = couponId;
        this.workspaceId = workspaceId;
        this.providerId = providerId;
    }

    public static CouponIssueCountKey of(Long couponId, String workspaceId, String providerId) {
        return CouponIssueCountKey.builder()
            .couponId(couponId)
            .workspaceId(workspaceId)
            .providerId(providerId)
            .build();
    }

    @Override
    public String getKey() {
        return "";
    }

    @Override
    public Long deserializeValue(String value) {
        if (value == null) {
            return DEFAULT_VALUE;
        }
        try {
            return Long.valueOf(value);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                String.format("Error during deserialization. value: (%s)", value));
        }
    }

    @Override
    public String serializeValue(Long value) {
        return "";
    }

    @Override
    public Duration getTtl() {
        return null;
    }


}
