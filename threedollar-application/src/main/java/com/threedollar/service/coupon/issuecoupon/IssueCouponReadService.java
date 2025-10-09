package com.threedollar.service.coupon.issuecoupon;

import com.threedollar.controller.coupon.dto.IssueCouponAndCursor;
import com.threedollar.domain.coupon.IssueCoupon;
import com.threedollar.domain.coupon.IssueCouponStatus;

import com.threedollar.service.coupon.issuecoupon.dto.response.IssueCouponResponse;

import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class IssueCouponReadService {

    private final IssueCouponQueryService issueCouponQueryService;

    public List<IssueCouponResponse> findCouponByOwner(String workspaceId, String ticketId, String ownerId, Set<IssueCouponStatus> status) {
        return issueCouponQueryService.findIssueCouponsByOwner(workspaceId, ticketId, ownerId,
            status).stream()
            .map(IssueCouponResponse::from)
            .toList();
    }

    public IssueCouponAndCursor getIssueCouponsWithCursor(String workspaceId, String ticketId, String ownerId,
        Set<IssueCouponStatus> status, int size) {
        List<IssueCoupon> issueCoupons = issueCouponQueryService.findIssueCouponsByOwnerWithLimit(
            workspaceId, ticketId, ownerId, status,size + 1);
        return getIssueCouponAndCursor(issueCoupons, size);
    }

    private IssueCouponAndCursor getIssueCouponAndCursor(List<IssueCoupon> issueCoupons, int size) {
        if (issueCoupons.isEmpty() || issueCoupons.size() <= size) {
            return IssueCouponAndCursor.noMore(issueCoupons);
        }
        return IssueCouponAndCursor.hasMore(issueCoupons);
    }



}
