package com.threedollar.domain.coupon.repository;

import com.threedollar.domain.coupon.CouponGroup;
import com.threedollar.domain.coupon.CouponUsageStatus;
import com.threedollar.domain.coupon.usercoupon.UserCoupon;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long>, UserCouponRepositoryCustom {

    List<UserCoupon> findByWorkspaceIdAndCouponGroupAndAccountIdAndUsageStatus(
        String workspaceId, CouponGroup couponGroup, String accountId, CouponUsageStatus status);

}
