package com.threedollar.service.coupon;

import com.threedollar.domain.coupon.Coupon;

import com.threedollar.domain.coupon.repository.redis.couponissuecount.CouponIssueCountKey;
import com.threedollar.domain.coupon.repository.redis.couponissuecount.CouponIssueCountRepository;

import java.util.concurrent.CompletableFuture;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponIssuanceService {

    private final CouponIssueCountRepository couponIssueCountRepository;

    // 쿠폰 발급이 불가능한 상태를 비동기적으로 처리합니다.
    @Async
    public void disableIssuanceCoupon(Coupon coupon) {
        CouponIssueCountKey key = CouponIssueCountKey.of(coupon.getId(), coupon.getWorkspaceId(), coupon.getProviderId());
        if (coupon.getMaxCount() >= couponIssueCountRepository.getValueByKey(key)) {
            coupon.disableIssuable();
        }
        CompletableFuture.completedFuture(null);
    }


}
