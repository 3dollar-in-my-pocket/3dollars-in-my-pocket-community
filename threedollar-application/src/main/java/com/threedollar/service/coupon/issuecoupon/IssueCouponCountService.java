package com.threedollar.service.coupon.issuecoupon;

import com.threedollar.domain.coupon.IssueCouponCountKey;
import com.threedollar.domain.coupon.repository.IssueCouponCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class IssueCouponCountService {

    private final IssueCouponCountRepository issueCouponCountRepository;

    public long incrCouponCount(String workspaceId, String ticketId, Long couponId) {
        return issueCouponCountRepository.incr(IssueCouponCountKey.of(workspaceId, ticketId, couponId));
    }

}
