package com.threedollar.controller.coupon;

import com.threedollar.domain.coupon.Coupon;
import com.threedollar.domain.coupon.CouponStatus;

import com.threedollar.service.coupon.CouponQueryService;

import com.threedollar.service.coupon.dto.response.CouponResponse;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CouponReadService {

    private final CouponQueryService couponQueryService;

    public CouponResponse getCouponById(String workspaceId, String ticketId, Long couponId) {
        Coupon coupon = couponQueryService.couponById(workspaceId, ticketId, couponId,
            List.of(CouponStatus.ACTIVE));
        return new CouponResponse().from(coupon);
    }

}
