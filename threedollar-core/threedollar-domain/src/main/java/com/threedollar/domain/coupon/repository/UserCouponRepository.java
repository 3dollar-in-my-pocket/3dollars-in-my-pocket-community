package com.threedollar.domain.coupon.repository;

import com.threedollar.domain.coupon.usercoupon.UserCoupon;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long>, UserCouponRepositoryCustom {

}
