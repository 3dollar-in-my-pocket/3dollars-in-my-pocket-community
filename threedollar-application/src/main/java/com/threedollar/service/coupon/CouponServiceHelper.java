package com.threedollar.service.coupon;


import com.threedollar.config.cache.CacheType;
import com.threedollar.domain.coupon.Coupon;

import com.threedollar.domain.coupon.repository.CouponRepository;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CouponServiceHelper {

    private final CouponRepository couponRepository;

    @Transactional
    public List<Coupon> getCouponList(List<Long> couponIds) {
        return couponRepository.findByIdIn(couponIds);
    }

}
