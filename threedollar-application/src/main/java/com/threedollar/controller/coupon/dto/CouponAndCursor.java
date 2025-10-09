package com.threedollar.controller.coupon.dto;

import com.threedollar.common.dto.response.CursorResponse;
import com.threedollar.domain.coupon.Coupon;
import com.threedollar.service.coupon.dto.response.CouponResponse;

import java.util.List;

import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CouponAndCursor {

    private CursorResponse cursor;

    private List<CouponResponse> coupons;

    @Builder(access = AccessLevel.PRIVATE)
    public CouponAndCursor(CursorResponse cursor, List<CouponResponse> coupons) {
        this.cursor = cursor;
        this.coupons = coupons;
    }

    public static CouponAndCursor hasMore(List<Coupon> coupons) {
        return CouponAndCursor.builder()
            .cursor(CursorResponse.builder()
                .hasNext(true)
                .hasMore(true)
                .nextCursor(coupons.get(coupons.size() - 2).getId())
                .build())
            .coupons(getCouponResponse(coupons.subList(0, coupons.size() - 1)))
            .build();
    }

    public static CouponAndCursor noMore(List<Coupon> coupons) {
        return CouponAndCursor.builder()
            .cursor(CursorResponse.builder()
                .hasNext(false)
                .hasMore(false)
                .nextCursor(null)
                .build())
            .coupons(getCouponResponse(coupons))
            .build();
    }


    private static List<CouponResponse> getCouponResponse(List<Coupon> coupons) {
        return coupons.stream()
            .map(CouponResponse::from)
            .collect(Collectors.toList());
    }


}
