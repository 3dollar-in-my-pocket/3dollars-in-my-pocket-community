package com.threedollar.service.coupon;

import com.threedollar.domain.coupon.DateTimeInterval;
import com.threedollar.domain.coupon.IssueCoupon;
import com.threedollar.domain.coupon.repository.IssueCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponIssueService {

    private final IssueCouponRepository issueCouponRepository;

    public void issueCoupon(String workspaceId, String ticketId, String ownerId, Long couponId, DateTimeInterval usableDateTime) {
        IssueCoupon issueCoupon = IssueCoupon.newInstance(workspaceId, ticketId, ownerId, couponId,
            usableDateTime);
        issueCouponRepository.save(issueCoupon);
    }
}
