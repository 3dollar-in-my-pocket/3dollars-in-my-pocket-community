package com.threedollar.service.coupon.issuecoupon.dto.request;

import com.threedollar.domain.coupon.IssueCouponStatus;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class IssueCouponFilter {

    private Set<IssueCouponStatus> status = Set.of(IssueCouponStatus.ISSUED);

    public IssueCouponFilter(Set<IssueCouponStatus> status) {
        this.status = status;
    }

}
