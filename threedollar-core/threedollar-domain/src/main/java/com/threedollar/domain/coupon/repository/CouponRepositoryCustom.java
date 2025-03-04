package com.threedollar.domain.coupon.repository;

import com.threedollar.domain.coupon.Coupon;

import com.threedollar.domain.coupon.CouponGroup;

import java.util.List;

public interface CouponRepositoryCustom {

    List<Coupon> findByCouponInfoAndSize(String workspaceId, CouponGroup couponGroup,
        String targetId, int size);

}
