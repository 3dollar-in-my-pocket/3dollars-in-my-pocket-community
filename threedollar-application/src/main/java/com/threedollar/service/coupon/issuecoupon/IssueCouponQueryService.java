package com.threedollar.service.coupon.issuecoupon;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class IssueCouponQueryService {

    private final IssueCouponRepository issueCouponRepository;

    public boolean existsIssueCoupon(String workspaceId, String ticketId, Long couponId, String ownerId) {
        return issueCouponRepository.existsByWorkspaceIdAndTicketIdAndIdAndOwnerId(
            workspaceId, ticketId, couponId, ownerId
        );
    }
}
