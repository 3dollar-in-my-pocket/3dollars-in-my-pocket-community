package com.threedollar.controller.coupon.dto.request;

import com.threedollar.domain.coupon.CouponGroup;
import com.threedollar.domain.coupon.usercoupon.UserCoupon;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class IssueCouponRequest {

    @NotBlank
    private String accountId;

    private LocalDateTime issuedAt;

    private LocalDateTime validPeriodStart;

    private LocalDateTime validPeriodEnd;

    public IssueCouponRequest(String accountId, LocalDateTime issuedAt,
        LocalDateTime validPeriodStart, LocalDateTime validPeriodEnd) {
        this.accountId = accountId;
        this.issuedAt = issuedAt;
        this.validPeriodStart = validPeriodStart;
        this.validPeriodEnd = validPeriodEnd;
    }

    public UserCoupon toEntity(String workspaceId, CouponGroup couponGroup, Long couponId) {
        return UserCoupon.newInstance(
            couponId, workspaceId, couponGroup, accountId, issuedAt, validPeriodStart,
            validPeriodEnd);

    }

}
