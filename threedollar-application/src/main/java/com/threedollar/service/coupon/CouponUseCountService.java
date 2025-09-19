package com.threedollar.service.coupon;

import com.threedollar.domain.coupon.repository.CouponUseCountKey;
import com.threedollar.domain.coupon.repository.CouponUseCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponUseCountService {

    private final CouponUseCountRepository couponUseCountRepository;

    public long incrCouponUseCount(String workspaceId, String ticketId, Long couponId) {
        return couponUseCountRepository.incr(CouponUseCountKey.of(workspaceId, ticketId, couponId));
    }

}
