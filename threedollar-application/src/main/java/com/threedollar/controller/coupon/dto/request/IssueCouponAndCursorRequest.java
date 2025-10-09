package com.threedollar.controller.coupon.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class IssueCouponAndCursorRequest {

    private int size;

    public IssueCouponAndCursorRequest(int size) {
        this.size = size;
    }
}
