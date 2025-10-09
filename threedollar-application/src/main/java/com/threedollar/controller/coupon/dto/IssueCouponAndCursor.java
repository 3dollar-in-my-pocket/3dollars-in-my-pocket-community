package com.threedollar.controller.coupon.dto;

import com.threedollar.common.dto.response.CursorResponse;
import com.threedollar.domain.coupon.IssueCoupon;
import com.threedollar.service.coupon.issuecoupon.dto.response.IssueCouponResponse;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class IssueCouponAndCursor {

    private CursorResponse cursor;

    private List<IssueCouponResponse> issueCouponResponse;

    @Builder(access = AccessLevel.PRIVATE)
    public IssueCouponAndCursor(CursorResponse cursor,
        List<IssueCouponResponse> issueCouponResponse) {
        this.cursor = cursor;
        this.issueCouponResponse = issueCouponResponse;
    }

    public static IssueCouponAndCursor hasMore(List<IssueCoupon> issueCoupons) {
        return IssueCouponAndCursor.builder()
            .cursor(CursorResponse.builder()
                .hasNext(true)
                .hasMore(true)
                .nextCursor(issueCoupons.get(issueCoupons.size() - 2).getId())
                .build())
            .issueCouponResponse(getIssueCouponResponse(issueCoupons.subList(0, issueCoupons.size() - 1)))
            .build();
    }

    public static IssueCouponAndCursor noMore(List<IssueCoupon> issueCoupons) {
        return IssueCouponAndCursor.builder()
            .cursor(CursorResponse.builder()
                .hasNext(false)
                .hasMore(false)
                .nextCursor(null)
                .build())
            .issueCouponResponse(getIssueCouponResponse(issueCoupons))
            .build();
    }

    private static List<IssueCouponResponse> getIssueCouponResponse(List<IssueCoupon> issueCoupons) {
        return issueCoupons.stream()
            .map(IssueCouponResponse::from)
            .toList();
    }
}
