package com.threedollar.domain.coupon.repository.redis;

public interface UsedCouponCountRepository {

    void incrByCount(Long couponId, String workspaceId, String providerId);

    Long getValueByKey(UsedCouponCountKey key);

}
