package com.threedollar.domain.coupon;

import com.threedollar.common.util.JsonUtils;
import com.threedollar.infra.redis.StringRedisKey;

import java.time.Duration;

import lombok.Builder;
import lombok.Getter;

@Getter
public class IssueCouponCountKey implements StringRedisKey<IssueCouponCountKey, Long> {

    private static final long DEFAULT_VALUE = 0L;

    private final String workspaceId;

    private final String ticketId;

    private final Long couponId;

    public static IssueCouponCountKey of(String workspaceId, String ticketId, Long couponId) {
        return IssueCouponCountKey.builder()
            .ticketId(ticketId)
            .workspaceId(workspaceId)
            .couponId(couponId)
            .build();
    }

    @Builder
    public IssueCouponCountKey(String workspaceId, String ticketId, Long couponId) {
        this.workspaceId = workspaceId;
        this.ticketId = ticketId;
        this.couponId = couponId;
    }

    @Override
    public String getKey() {
        return "couponIssue:workspaceId" + workspaceId + ":ticketId:" + ticketId + ":couponId:" + couponId;
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
