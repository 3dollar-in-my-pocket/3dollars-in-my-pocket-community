package com.threedollar.domain.coupon.repository;

import com.threedollar.domain.coupon.CouponGroup;
import com.threedollar.domain.coupon.CouponUsageStatus;
import com.threedollar.domain.coupon.usercoupon.UserCoupon;

import java.util.List;

public interface UserCouponRepositoryCustom {

    List<UserCoupon> findUserCoupon(String workspaceId, CouponGroup couponGroup,
        String accountId, CouponUsageStatus usageStatus, Long cursor, int size);


}
