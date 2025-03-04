package com.threedollar.service.coupon;

import com.threedollar.domain.coupon.Coupon;
import com.threedollar.domain.coupon.CouponGroup;
import com.threedollar.domain.coupon.repository.CouponRepository;
import com.threedollar.service.coupon.dto.request.AddCouponRequest;
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
        String targetId, int size) {
        List<CouponResponse> couponList = couponRepository.findByCouponInfoAndSize(
            workspaceId, couponGroup, targetId, size + 1)
            .stream().map(CouponResponse::of).toList();

        if (couponList.isEmpty() || couponList.size() <= size) {
            return CouponAndCursorResponse.noMore(couponList);
        }
        return CouponAndCursorResponse.hasNext(couponList);
    }


    @Transactional
    public void addCoupon(String workspaceId, CouponGroup couponGroup, String targetId,
        AddCouponRequest request) {
        Coupon coupon = request.toEntity(workspaceId, couponGroup, targetId);
        couponRepository.save(coupon);
    }

}
