package com.threedollar.domain.coupon.repository;

import com.threedollar.domain.coupon.Coupon;

import com.threedollar.domain.coupon.CouponGroup;

import java.time.LocalDateTime;
import java.util.List;

public interface CouponRepositoryCustom {

    List<Coupon> findByCouponInfoAndSize(String workspaceId, CouponGroup couponGroup,
        String providerId, String creatorId, int size);

    List<Coupon> findValidCouponByProviderInfo(String workspaceId, CouponGroup couponGroup,
        String providerId, LocalDateTime now);

    Coupon findValidCouponByCouponInfo(String workspaceId, CouponGroup couponGroup,
        Long couponId, LocalDateTime now);


}
