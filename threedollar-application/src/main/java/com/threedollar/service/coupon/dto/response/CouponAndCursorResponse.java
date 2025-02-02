package com.threedollar.service.coupon.dto.response;

import com.threedollar.common.dto.response.CursorResponse;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CouponAndCursorResponse {

    private CursorResponse cursorResponse;

    private List<CouponResponse> coupons;

    @Builder
    public CouponAndCursorResponse(CursorResponse cursorResponse, List<CouponResponse> coupons) {
        this.cursorResponse = cursorResponse;
        this.coupons = coupons;
    }

    public static CouponAndCursorResponse hasNext(List<CouponResponse> coupons) {
        return CouponAndCursorResponse.builder()
            .cursorResponse(CursorResponse.builder()
                .nextCursor(coupons.get(coupons.size() - 2).getCouponId())
                .hasNext(true)
                .build())
            .coupons(coupons)
            .build();
    }

    public static CouponAndCursorResponse noMore(List<CouponResponse> coupons) {
        return CouponAndCursorResponse.builder()
            .cursorResponse(CursorResponse.builder()
                .nextCursor(null)
                .hasNext(false)
                .build())
            .coupons(coupons)
            .build();
    }
}
