package com.threedollar.domain.coupon;

import com.threedollar.domain.BaseEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class IssueCoupon extends BaseEntity {

    @Column(nullable = false, length = 30)
    private String workspaceId;

    @Column(nullable = false, length = 30)
    private String ticketId;

    @Column(nullable = false, length = 100)
    private String ownerId;

    @Column(nullable = false, length = 100)
    private Long couponId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private IssueCouponStatus status;

    @AttributeOverrides({
        @AttributeOverride(name = "startDateTime", column = @Column(name = "issue_start_datetime", nullable = false)),
        @AttributeOverride(name = "endDateTime", column = @Column(name = "issue_end_datetime", nullable = false))
    })
    private DateTimeInterval usableDateTime;

    @Column(nullable = false)
    private LocalDateTime issuedDateTime;

    @Builder(access = AccessLevel.PRIVATE)
    public IssueCoupon(String workspaceId, String ticketId, String ownerId, Long couponId,
        IssueCouponStatus status, DateTimeInterval usableDateTime, LocalDateTime issuedDateTime) {
        this.workspaceId = workspaceId;
        this.ticketId = ticketId;
        this.ownerId = ownerId;
        this.couponId = couponId;
        this.status = status;
        this.usableDateTime = usableDateTime;
        this.issuedDateTime = issuedDateTime;
    }

    public static IssueCoupon newInstance(String workspaceId, String ticketId, String ownerId, Long couponId,
        DateTimeInterval usableDateTime) {
        return IssueCoupon.builder()
            .workspaceId(workspaceId)
            .ticketId(ticketId)
            .ownerId(ownerId)
            .couponId(couponId)
            .status(IssueCouponStatus.ISSUED)
            .usableDateTime(usableDateTime)
            .issuedDateTime(LocalDateTime.now())
            .build();
    }

    public void use() {
        if (this.status == IssueCouponStatus.USED) {
            throw new IllegalStateException("이미 사용된 쿠폰입니다.");
        }
        if (this.status == IssueCouponStatus.DELETED) {
            throw new IllegalStateException("삭제된 쿠폰입니다.");
        }
        
        LocalDateTime now = LocalDateTime.now();
        if (usableDateTime.getStartDateTime().isAfter(now) || usableDateTime.getEndDateTime().isBefore(now)) {
            throw new IllegalArgumentException("현재 시점에서 사용 가능한 쿠폰이 아닙니다.");
        }
        
        this.status = IssueCouponStatus.USED;
    }
}


