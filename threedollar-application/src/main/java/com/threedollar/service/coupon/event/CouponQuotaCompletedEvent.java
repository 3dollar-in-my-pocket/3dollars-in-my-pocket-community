package com.threedollar.service.coupon.event;

/**
 * 쿠폰 발급 쿼터가 정확히 소진되었을 때 발생하는 도메인 이벤트.
 */
public record CouponQuotaCompletedEvent(Long couponId, String workspaceId, String ticketId, Long maxIssuableCount) {
}

