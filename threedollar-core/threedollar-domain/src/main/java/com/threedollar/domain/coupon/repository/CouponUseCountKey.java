package com.threedollar.domain.coupon.repository;

import com.threedollar.common.util.JsonUtils;
import com.threedollar.infra.redis.StringRedisKey;

import java.time.Duration;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CouponUseCountKey implements StringRedisKey<CouponUseCountKey, Long> {

    private static final long DEFAULT_VALUE = 0L;

    private final String workspaceId;

    private final String ticketId;

    private final Long couponId;

    @Builder(access = AccessLevel.PRIVATE)
    public CouponUseCountKey(String workspaceId, String ticketId, Long couponId) {
        this.workspaceId = workspaceId;
        this.ticketId = ticketId;
        this.couponId = couponId;
    }

    public static CouponUseCountKey of(String workspaceId, String ticketId, Long couponId) {
        return CouponUseCountKey.builder()
            .ticketId(ticketId)
            .workspaceId(workspaceId)
            .couponId(couponId)
            .build();
    }

    @Override
    public String getKey() {
        return "couponUse:workspaceId" + workspaceId + ":ticketId:" + ticketId + ":couponId:" + couponId;
    }

    @Override
    public Long deserializeValue(String value) {
        if (value == null) {
            return DEFAULT_VALUE;
        }
        try {
            return Long.valueOf(value);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("역직렬화 중 에러가 발생했습니다. value: (%s)", value));
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
