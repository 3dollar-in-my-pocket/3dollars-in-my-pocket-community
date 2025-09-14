package com.threedollar.domain.coupon;

import lombok.Getter;

@Getter
public enum IssueCouponStatus {

    ISSUED("발급됨"),
    USED("사용됨"),
    DELETED("삭제됨")
    ;

    private final String description;

    IssueCouponStatus(String description) {
        this.description = description;
    }
}
