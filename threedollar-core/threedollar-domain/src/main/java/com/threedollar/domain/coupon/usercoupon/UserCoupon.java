package com.threedollar.domain.coupon.usercoupon;

import com.threedollar.common.exception.InvalidException;
import com.threedollar.domain.BaseEntity;
import com.threedollar.domain.coupon.CouponGroup;
import com.threedollar.domain.coupon.CouponUsageStatus;
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
    private LocalDateTime issuedAt;

    @Column(nullable = false)
    private LocalDateTime validPeriodStart;

    @Column(nullable = false)
    private LocalDateTime validPeriodEnd;

    @Column
    private String description;

    @Builder(access = AccessLevel.PROTECTED)
    public UserCoupon(Long couponId, String workspaceId, CouponGroup couponGroup, String accountId, LocalDateTime usedAt,
        CouponUsageStatus usageStatus, LocalDateTime issuedAt, LocalDateTime validPeriodStart,
        LocalDateTime validPeriodEnd, String description) {
        this.couponId = couponId;
        this.workspaceId = workspaceId;
        this.couponGroup = couponGroup;
        this.accountId = accountId;
        this.usedAt = usedAt;
        this.usageStatus = usageStatus;
        this.issuedAt = issuedAt;
        this.validPeriodStart = validPeriodStart;
        this.validPeriodEnd = validPeriodEnd;
        this.description = description;
    }

    public static UserCoupon newInstance(@NotNull Long couponId, @NotBlank String workspaceId,
        @NotNull CouponGroup couponGroup, @NotBlank String accountId, LocalDateTime issuedAt,
        LocalDateTime validPeriodStart, LocalDateTime validPeriodEnd) {
        return UserCoupon.builder()
            .couponId(couponId)
            .accountId(accountId)
            .workspaceId(workspaceId)
            .couponGroup(couponGroup)
            .usageStatus(CouponUsageStatus.UNUSED)
            .issuedAt(issuedAt)
            .validPeriodStart(validPeriodStart)
            .validPeriodEnd(validPeriodEnd)
            .build();
    }

    public void use() {
        if (this.usageStatus != CouponUsageStatus.UNUSED) {
            throw new InvalidException("쿠폰은 이미 사용되었거나 만료되었습니다.");
        }
        if (LocalDateTime.now().isBefore(this.validPeriodStart) || LocalDateTime.now().isAfter(this.validPeriodEnd)) {
            throw new InvalidException("쿠폰 사용 기간이 아닙니다.");
        }
        this.usedAt = LocalDateTime.now();
        this.usageStatus = CouponUsageStatus.USED;
    }
}
