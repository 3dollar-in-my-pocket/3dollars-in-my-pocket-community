package com.threedollar.service.coupon.dto.request;

import com.threedollar.domain.coupon.Coupon;
import com.threedollar.domain.coupon.CouponGroup;
import com.threedollar.domain.coupon.CouponTag;
import com.threedollar.domain.coupon.CouponTime;
import com.threedollar.domain.coupon.CouponType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AddCouponRequest {

    @NotBlank
    private String name;

    @NotNull
    private CouponType couponType;

    @NotNull
    private CouponTag couponTag;

    private CouponTime couponTime;

    private Long count;

    @NotBlank
    private String accountId;

    public AddCouponRequest(String name, CouponType couponType, CouponTag couponTag,
        CouponTime couponTime, Long count, String accountId) {
        this.name = name;
        this.couponType = couponType;
        this.couponTag = couponTag;
        this.couponTime = couponTime;
        this.count = count;
        this.accountId = accountId;
    }

    public Coupon toEntity(String workspaceId, CouponGroup couponGroup, String targetId) {
        return Coupon.newInstance(workspaceId, targetId, name, couponType, couponTag,
            couponGroup, count, couponTime, accountId);
    }
}
