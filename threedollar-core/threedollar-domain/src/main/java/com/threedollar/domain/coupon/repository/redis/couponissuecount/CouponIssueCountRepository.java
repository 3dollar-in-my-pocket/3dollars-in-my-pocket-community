package com.threedollar.domain.coupon.repository.redis.couponissuecount;

public interface CouponIssueCountRepository {

    void incrByCount(Long couponId, String workspaceId, String providerId);
    Long getValueByKey(CouponIssueCountKey key);

}
