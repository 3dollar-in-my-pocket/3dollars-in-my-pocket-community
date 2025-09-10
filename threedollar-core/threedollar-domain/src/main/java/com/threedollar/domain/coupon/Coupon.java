package com.threedollar.domain.coupon;

import com.threedollar.domain.BaseEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Coupon extends BaseEntity {

    @Column(nullable = false, length = 30)
    private String workspaceId; // 서비스 ID

    @Column(nullable = false, length = 30)
    private String ticketId; // 서비스별 사용처 (e.g. 가게 쿠폰, 광고 쿠폰 등)

    @Column(nullable = false, length = 100)
    private String providerId; // 쿠폰 제공자 ID (가게 쿠폰 -> 가게 ID)

    @Column(nullable = false, length = 100)
    private String creatorId; // 쿠폰 생성자 ID (관리자 ID)

    @Column(nullable = false, length = 100)
    private String name; // 쿠폰명

    @Column(length = 300)
    private String description; // 쿠폰 설명

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private CouponStatus status; // 쿠폰 상태

    @AttributeOverrides({
        @AttributeOverride(name = "startDateTime", column = @Column(name = "issuable_start_date_time")),
        @AttributeOverride(name = "endDateTime", column = @Column(name = "issuable_end_date_time"))
    })
    private DateTimeInterval issueDateTime; // 쿠폰 발급 가능 기간

    @AttributeOverrides({
        @AttributeOverride(name = "startDateTime", column = @Column(name = "usable_start_date_time")),
        @AttributeOverride(name = "endDateTime", column = @Column(name = "usable_end_date_time"))
    })
    private DateTimeInterval usableDateTime; // 쿠폰 사용 가능 기간

    @Column
    private Long maxIssuableCount; // 발급 가능 수량 (null이면 무제한)

    @Builder(access = AccessLevel.PRIVATE)
    private Coupon(String workspaceId, String ticketId, String providerId, String creatorId,
        String name, String description, CouponStatus status, DateTimeInterval issueDateTime,
        DateTimeInterval usableDateTime, Long maxIssuableCount) {
        this.workspaceId = workspaceId;
        this.ticketId = ticketId;
        this.providerId = providerId;
        this.creatorId = creatorId;
        this.name = name;
        this.description = description;
        this.status = status;
        this.issueDateTime = issueDateTime;
        this.usableDateTime = usableDateTime;
        this.maxIssuableCount = maxIssuableCount;
    }

    public static Coupon newInstance(String workspaceId, String ticketId, String providerId, String creatorId,
        String name, String description, CouponStatus status, DateTimeInterval issueDateTime,
        DateTimeInterval usableDateTime, Long maxIssuableCount) {
        return Coupon.builder()
            .workspaceId(workspaceId)
            .ticketId(ticketId)
            .providerId(providerId)
            .creatorId(creatorId)
            .name(name)
            .description(description)
            .status(status)
            .issueDateTime(issueDateTime)
            .usableDateTime(usableDateTime)
            .maxIssuableCount(maxIssuableCount)
            .build();
    }

    public boolean hasLimitQuota() {
        return this.maxIssuableCount != null;
    }


}
