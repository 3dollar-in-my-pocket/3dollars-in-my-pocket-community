package com.threedollar.domain.coupon;

import lombok.Getter;

@Getter
public enum CouponGroup {
    BOSS_STORE("사장님 가게")
    ,ADVERTISEMENT_COUPON("광고 쿠폰")
    ,EVENT("이벤트 쿠폰")
    ,DISCOUNT("할인 쿠폰")
    ;
    private final String description;
    CouponGroup(String description) {
        this.description = description;
    }
}
