package com.threedollar.domain.coupon.usercoupon;

import lombok.Getter;

@Getter
public enum UserCouponStatus {
    ACTIVE("활성화"),
    INACTIVE("삭제됨")
    ;

    private final String description;

    UserCouponStatus(String description) {
        this.description = description;
    }
}
