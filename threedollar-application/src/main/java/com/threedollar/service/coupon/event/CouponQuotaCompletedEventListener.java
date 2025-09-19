package com.threedollar.service.coupon.event;

import com.threedollar.domain.coupon.Coupon;
import com.threedollar.domain.coupon.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class CouponQuotaCompletedEventListener {

    private final CouponRepository couponRepository;

    @Async("couponAsyncExecutor")
    @Transactional
    @EventListener
    public void handle(CouponQuotaCompletedEvent event) {
        // 쿠폰 조회 (낙관적: 이미 ENDED 면 skip)
        Coupon coupon = couponRepository.findById(event.couponId())
            .orElse(null);
        if (coupon == null) {
            return;
        }
        coupon.endByQuota();
    }
}

