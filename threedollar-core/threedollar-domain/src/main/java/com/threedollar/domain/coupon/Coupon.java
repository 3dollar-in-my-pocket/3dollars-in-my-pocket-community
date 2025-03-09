package com.threedollar.domain.coupon;

import com.threedollar.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String workspaceId;

    @Column(nullable = false)
    private String targetId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CouponType couponType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CouponGroup couponGroup;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CouponStatus status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CouponTag couponTag;

    @Column(nullable = false)
    private Long count;

    @Embedded
    private CouponTime couponTime;

    @Column(nullable = false)
    private String accountId;

    @Builder(access = AccessLevel.PRIVATE)
    public Coupon(String workspaceId, String targetId, String name, CouponType couponType,
        CouponTag couponTag, CouponStatus status, CouponGroup couponGroup, CouponTime couponTime, Long count, String accountId) {
        this.workspaceId = workspaceId;
        this.targetId = targetId;
        this.name = name;
        this.couponTag = couponTag;
        this.couponType = couponType;
        this.status = status;
        this.couponGroup = couponGroup;
        this.count = count;
        this.couponTime = couponTime;
        this.accountId = accountId;
    }

    public static Coupon newInstance(String workspaceId, String targetId, String name,
        CouponType couponType, CouponTag couponTag, CouponGroup couponGroup, Long count,
        CouponTime couponTime, String accountId) {
        return Coupon.builder()
            .couponType(couponType)
            .workspaceId(workspaceId)
            .targetId(targetId)
            .name(name)
            .couponTag(couponTag)
            .couponTime(couponTime)
            .accountId(accountId)
            .count(count)
            .couponGroup(couponGroup)
            .status(CouponStatus.ACTIVE)
            .build();
    }

    public void descrease(LocalDateTime now) {
        // Validate time
        if (now.isBefore(couponTime.getStartTime()) || now.isAfter(couponTime.getEndTime())) {
            throw new IllegalArgumentException("쿠폰 사용 가능한 시간이 아닙니다.");
        }
        if (this.count <= 0) {
            throw new IllegalArgumentException("쿠폰의 재고가 부족합니다.");
        }
        this.count -= 1;
    }

}
