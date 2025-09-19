package com.threedollar.service.coupon.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class CouponExistenceResponse {

    private Long couponId;
    private boolean exists;

    private CouponExistenceResponse(Long couponId, boolean exists) {
        this.couponId = couponId;
        this.exists = exists;
    }

    public static CouponExistenceResponse of(Long couponId, boolean exists) {
        return new CouponExistenceResponse(couponId, exists);
    }

}
