package com.threedollar.service.coupon.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CouponExistence {

    private String providerId;

    private boolean exists;

    public CouponExistence(String providerId, boolean exists) {
        this.providerId = providerId;
        this.exists = exists;
    }
}
