package com.threedollar.domain.coupon.service;

import com.threedollar.domain.coupon.Coupon;
import com.threedollar.domain.coupon.repository.CouponRepository;
import com.threedollar.domain.coupon.CouponStatus;
import com.threedollar.domain.coupon.DateTimeInterval;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CouponCreateService {

    private final CouponRepository couponRepository;

    @Transactional
    public Coupon create(String workspaceId, String ticketId, String providerId, String creatorId, String name,
                         String description, Long maxIssuableCount, DateTimeInterval issueDateTime, DateTimeInterval usageDateTime) {
        Coupon coupon = Coupon.newInstance(workspaceId, ticketId, providerId, creatorId, name, description,
            CouponStatus.ACTIVE, issueDateTime, usageDateTime, maxIssuableCount);
        couponRepository.save(coupon);
        return coupon;

    }

}
