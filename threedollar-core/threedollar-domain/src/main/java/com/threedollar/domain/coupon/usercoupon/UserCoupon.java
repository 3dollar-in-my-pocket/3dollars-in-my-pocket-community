package com.threedollar.domain.coupon.usercoupon;

import com.threedollar.domain.BaseEntity;
import com.threedollar.domain.coupon.CouponGroup;
import com.threedollar.domain.coupon.CouponUsageStatus;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCoupon extends BaseEntity {

    @Column(nullable = false)
    private Long couponId;

    @Column(nullable = false)
    private String workspaceId;

    @Column(nullable = false)
    private CouponGroup couponGroup;

    @Column(nullable = false)
    private String accountId;

    @Column
    private LocalDateTime usedAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CouponUsageStatus usageStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserCouponStatus userCouponStatus;

    @Builder(access = AccessLevel.PROTECTED)
    public UserCoupon(Long couponId, String workspaceId, CouponGroup couponGroup, String accountId, LocalDateTime usedAt,
        CouponUsageStatus usageStatus, UserCouponStatus status) {
        this.couponId = couponId;
        this.workspaceId = workspaceId;
        this.couponGroup = couponGroup;
        this.accountId = accountId;
        this.usedAt = usedAt;
        this.usageStatus = usageStatus;
        this.userCouponStatus = status;
    }

    public static UserCoupon newInstance(@NotNull Long couponId, @NotBlank String workspaceId,
        @NotNull CouponGroup couponGroup, @NotBlank String accountId, @Nullable LocalDateTime usedAt) {
        return UserCoupon.builder()
            .couponId(couponId)
            .accountId(accountId)
            .workspaceId(workspaceId)
            .couponGroup(couponGroup)
            .status(UserCouponStatus.ACTIVE)
            .usageStatus(CouponUsageStatus.UNUSED)
            .usedAt(usedAt)
            .build();
    }
}
