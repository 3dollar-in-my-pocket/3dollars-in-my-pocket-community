package com.threedollar.service.coupon.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CouponUseRequest {

    private String ownerId;

    public CouponUseRequest(String ownerId) {
        this.ownerId = ownerId;
    }

}