package com.threedollar.domain.coupon.repository;

import com.threedollar.domain.coupon.Coupon;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long>, CouponRepositoryCustom {


}
