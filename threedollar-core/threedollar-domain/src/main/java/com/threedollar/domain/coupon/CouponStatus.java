package com.threedollar.domain.coupon;

import lombok.Getter;

@Getter
public enum CouponStatus {

    ACTIVE("활성화 상태"),
    ENDED("쿼터 종료 상태"),
    DELETED("삭제 상태"),
    ;

    private final String description;

    CouponStatus(String description) {
        this.description = description;
    }
}
