package com.threedollar.domain.coupon;

import com.threedollar.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

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
    private String accountId;

    @Column(nullable = false)
    private LocalDateTime usedAt;

    @Column(nullable = false)
    private CouponUsageStatus usageStatus;

    @Column(nullable = false)
    private CouponStatus status;

    @Builder(access = AccessLevel.PROTECTED)
    public UserCoupon(Long couponId, String accountId, LocalDateTime usedAt,
        CouponUsageStatus usageStatus, CouponStatus status) {
        this.couponId = couponId;
        this.accountId = accountId;
        this.usedAt = usedAt;
        this.usageStatus = usageStatus;
        this.status = status;
    }

    public static UserCoupon newInstance(Long couponId, String accountId, LocalDateTime usedAt) {
        return UserCoupon.builder()
            .couponId(couponId)
            .accountId(accountId)
            .status(CouponStatus.ACTIVE)
            .usageStatus(CouponUsageStatus.UNUSED)
            .usedAt(usedAt)
            .build();
    }
}
