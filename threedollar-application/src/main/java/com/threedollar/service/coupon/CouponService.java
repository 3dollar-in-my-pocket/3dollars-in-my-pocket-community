package com.threedollar.service.coupon;

import com.threedollar.domain.coupon.CouponGroup;
import com.threedollar.domain.coupon.repository.CouponRepository;
import com.threedollar.service.coupon.dto.response.CouponAndCursorResponse;
import com.threedollar.service.coupon.dto.response.CouponResponse;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional(readOnly = true)
    public CouponAndCursorResponse getCoupons(String workspaceId, CouponGroup couponGroup,
        String targetId, String accountId, int size) {
        List<CouponResponse> couponList = couponRepository.findByCouponInfoAndSize(
            workspaceId, couponGroup, targetId, accountId, size + 1)
            .stream().map(CouponResponse::of).toList();

        if (couponList.isEmpty() || couponList.size() <= size) {
            return CouponAndCursorResponse.noMore(couponList);
        }
        return CouponAndCursorResponse.hasNext(couponList);


    }



}
