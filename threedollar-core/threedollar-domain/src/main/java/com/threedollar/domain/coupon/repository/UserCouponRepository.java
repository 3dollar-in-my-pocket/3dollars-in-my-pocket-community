package com.threedollar.domain.coupon.repository;

import com.threedollar.domain.coupon.CouponGroup;
import com.threedollar.domain.coupon.usercoupon.UserCoupon;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long>, UserCouponRepositoryCustom {

    UserCoupon findByWorkspaceIdAndCouponGroupAndCouponIdAndAccountId(
        String workspaceId, CouponGroup couponGroup, Long couponId, String accountId);

    boolean existsByWorkspaceIdAndCouponGroupAndCouponIdAndAccountId(String workspaceId, CouponGroup couponGroup, Long couponId, String accountId);

}
