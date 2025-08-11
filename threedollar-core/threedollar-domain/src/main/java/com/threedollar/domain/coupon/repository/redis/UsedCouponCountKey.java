package com.threedollar.domain.coupon.repository.redis;

import com.threedollar.common.util.JsonUtils;

import com.threedollar.infra.redis.StringRedisKey;

import java.time.Duration;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UsedCouponCountKey implements StringRedisKey<UsedCouponCountKey, Long> {

    private static final long DEFAULT_VALUE = 0L;

    private final Long couponId;

    private final String workspaceId;

    private final String providerId;

    @Builder
    public UsedCouponCountKey(Long couponId, String workspaceId, String providerId) {
        this.couponId = couponId;
        this.workspaceId = workspaceId;
        this.providerId = providerId;
    }

    public static UsedCouponCountKey of(Long couponId, String workspaceId, String providerId) {
        return UsedCouponCountKey.builder()
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
        return JsonUtils.toJson(value);
    }

    @Override
    public Duration getTtl() {
        return null;
    }
}
