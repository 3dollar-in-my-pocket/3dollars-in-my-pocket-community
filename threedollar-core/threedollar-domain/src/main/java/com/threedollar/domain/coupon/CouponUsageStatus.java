package com.threedollar.domain.coupon;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CouponUsageStatus {
    UNUSED("사용 전"),
    USED("사용됨");

    private final String description;
}
