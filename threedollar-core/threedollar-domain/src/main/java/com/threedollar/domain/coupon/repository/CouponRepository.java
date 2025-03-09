package com.threedollar.domain.coupon.repository;

import com.threedollar.domain.coupon.Coupon;

import jakarta.persistence.LockModeType;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface CouponRepository extends JpaRepository<Coupon, Long>, CouponRepositoryCustom {

    List<Coupon> findByIdIn(List<Long> couponIds);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Coupon findWithLockById(Long couponId);

}
