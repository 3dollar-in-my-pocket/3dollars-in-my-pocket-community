package com.threedollar.service.coupon.dto.request;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CouponExistenceBulkRequest {

    @NotEmpty
    private Set<Long> couponIds;

    public CouponExistenceBulkRequest(Set<Long> couponIds) {
        this.couponIds = couponIds;
    }

}
