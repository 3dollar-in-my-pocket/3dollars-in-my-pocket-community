package com.threedollar.service.coupon.dto.request;

import com.threedollar.domain.coupon.CouponStatus;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CouponFilter {

    private Set<CouponStatus> status = Set.of(CouponStatus.ACTIVE);

    public CouponFilter(Set<CouponStatus> status) {
        this.status = status;
    }
}
