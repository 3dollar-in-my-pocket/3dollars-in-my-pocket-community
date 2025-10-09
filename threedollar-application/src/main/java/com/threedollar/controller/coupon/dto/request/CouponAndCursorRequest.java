package com.threedollar.controller.coupon.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CouponAndCursorRequest {

    private int size;

    public CouponAndCursorRequest(int size) {
        this.size = size;
    }
}
