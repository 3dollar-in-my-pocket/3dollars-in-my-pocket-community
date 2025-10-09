package com.threedollar.controller.coupon;

import com.threedollar.controller.coupon.dto.CouponAndCursor;
import com.threedollar.controller.coupon.dto.request.CouponAndCursorRequest;
import com.threedollar.domain.coupon.Coupon;
import com.threedollar.domain.coupon.CouponStatus;

import com.threedollar.service.coupon.CouponQueryService;

import com.threedollar.service.coupon.dto.response.CouponResponse;

import java.util.List;

import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CouponReadService {

    private final CouponQueryService couponQueryService;

    public CouponResponse getCouponById(String workspaceId, String ticketId, Long couponId) {
        Coupon coupon = couponQueryService.couponById(workspaceId, ticketId, couponId,
            List.of(CouponStatus.ACTIVE));
        return CouponResponse.from(coupon);
    }

    public List<CouponResponse> getCouponsByProvider(String workspaceId, String ticketId, String providerId,
        Set<CouponStatus> status) {
        List<Coupon> coupons = couponQueryService.findCouponsByProvider(workspaceId, ticketId, providerId, status);
        return coupons.stream()
            .map(CouponResponse::from)
            .toList();
    }

    public CouponAndCursor getCouponsWithCursor(String workspaceId, String ticketId, String providerId,
        Set<CouponStatus> status, CouponAndCursorRequest request) {
        List<Coupon> coupons = couponQueryService.findCouponsInfoWithLimit(workspaceId, ticketId, providerId, status,
            request.getSize() + 1);
        return getCouponAndCursor(coupons, request.getSize());
    }

    private CouponAndCursor getCouponAndCursor(List<Coupon> coupons, int size) {
        if (coupons.isEmpty() || coupons.size() <= size) {
            return CouponAndCursor.noMore(coupons);
        }
        return CouponAndCursor.hasMore(coupons);
    }

}
