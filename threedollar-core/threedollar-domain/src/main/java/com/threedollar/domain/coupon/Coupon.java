package com.threedollar.domain.coupon;

import com.threedollar.common.exception.ErrorCode;
import com.threedollar.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

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
    private String providerId; // 발급한 가게 Id

    @Column(nullable = false)
    private String creatorId; // 쿠폰 등록자 Id

    @Column(nullable = false)
    private String name;

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
    private Long maxCount;

    @Embedded
    private CouponTime couponTime;

    @Column(length = 1000)
    private String description;

    @Builder(access = AccessLevel.PRIVATE)
    public Coupon(String workspaceId, String providerId, String creatorId, String name,
        CouponTag couponTag, CouponStatus status, CouponGroup couponGroup,
        CouponTime couponTime, Long maxCount, String description) {
        this.workspaceId = workspaceId;
        this.providerId = providerId;
        this.creatorId = creatorId;
        this.name = name;
        this.couponTag = couponTag;
        this.status = status;
        this.couponGroup = couponGroup;
        this.maxCount = maxCount;
        this.couponTime = couponTime;
        this.description = description;
    }

    public static Coupon newInstance(String workspaceId, String providerId, String creatorId,
        String name, CouponTag couponTag, CouponGroup couponGroup,
        Long maxCount, CouponTime couponTime) {
        return Coupon.builder()
            .workspaceId(workspaceId)
            .providerId(providerId)
            .creatorId(creatorId)
            .name(name)
            .couponTag(couponTag)
            .couponTime(couponTime)
            .maxCount(maxCount)
            .couponGroup(couponGroup)
            .status(CouponStatus.ACTIVE)
            .build();
    }

    public void issueCoupon() {
        // Validate time
        CouponTime.validateCouponTime(couponTime.getIssueStartTime(), couponTime.getIssueEndTime(),
            ErrorCode.E400_INVALID_COUPON_ISSUE_PERIOD);
        if (this.limitCount == null || this.limitCount == 0) {
            throw new IllegalArgumentException("쿠폰의 재고가 부족합니다.");
        }
        this.limitCount -= 1; // 쿠폰 발급 시 재고 감소
    }

}
