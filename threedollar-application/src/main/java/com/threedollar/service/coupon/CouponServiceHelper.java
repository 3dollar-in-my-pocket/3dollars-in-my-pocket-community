package com.threedollar.service.coupon;


import com.threedollar.domain.coupon.Coupon;

import com.threedollar.domain.coupon.repository.CouponRepository;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CouponServiceHelper {

    private final CouponRepository couponRepository;

    // TODO: Add cache
    @Transactional
    public List<Coupon> getCouponList(List<Long> couponIds) {
        return couponRepository.findByIdIn(couponIds);
    }

}
