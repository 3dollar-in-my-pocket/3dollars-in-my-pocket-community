package com.threedollar.service.usercoupon.dto.response;

import com.threedollar.domain.coupon.CouponGroup;

import com.threedollar.domain.coupon.CouponUsageStatus;

import com.threedollar.domain.coupon.usercoupon.UserCoupon;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserCouponResponse {

    private Long couponId;

    private String workspaceId;

    private CouponGroup couponGroup;

    private String accountId;

    private LocalDateTime usedAt;

    private CouponUsageStatus couponUsageStatus;

    @Builder
    public UserCouponResponse(Long couponId, String workspaceId, CouponGroup couponGroup,
        String accountId, LocalDateTime usedAt, CouponUsageStatus couponUsageStatus) {
        this.couponId = couponId;
        this.workspaceId = workspaceId;
        this.couponGroup = couponGroup;
        this.accountId = accountId;
        this.usedAt = usedAt;
        this.couponUsageStatus = couponUsageStatus;
    }

    public static UserCouponResponse of(UserCoupon userCoupon) {
        return UserCouponResponse.builder()
            .couponId(userCoupon.getCouponId())
            .workspaceId(userCoupon.getWorkspaceId())
            .couponGroup(userCoupon.getCouponGroup())
            .accountId(userCoupon.getAccountId())
            .couponUsageStatus(userCoupon.getUsageStatus())
            .usedAt(userCoupon.getUsedAt())
            .build();
    }
}
