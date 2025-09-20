package com.threedollar.service.coupon.issuecoupon;

import com.threedollar.common.exception.NotFoundException;
import com.threedollar.domain.coupon.IssueCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class IssueCouponQueryService {

    private final IssueCouponRepository issueCouponRepository;

    public boolean existsIssueCoupon(String workspaceId, String ticketId, Long couponId, String ownerId) {
        return issueCouponRepository.existsByWorkspaceIdAndTicketIdAndIdAndOwnerId(
            workspaceId, ticketId, couponId, ownerId
        );
    }

    @Transactional
    public IssueCoupon findIssueCoupon(String workspaceId, String ticketId, Long couponId, String ownerId) {
        IssueCoupon issueCoupon = issueCouponRepository.findByWorkspaceIdAndTicketIdAndCouponIdAndOwnerId(
            workspaceId, ticketId, couponId, ownerId);
        if (issueCoupon == null) {
            throw new NotFoundException(String.format("발급된 쿠폰 (%s) 을 찾을 수 없습니다.", couponId));
        }
        return issueCoupon;
    }
}
