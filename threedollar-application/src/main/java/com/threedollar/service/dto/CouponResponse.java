package com.threedollar.service.dto;

import com.threedollar.domain.coupon.Coupon;
import com.threedollar.domain.coupon.CouponStatus;
import com.threedollar.domain.coupon.DateTimeInterval;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CouponResponse {

    private Long couponId;

    private String workspaceId;

    private String ticketId;

    private String providerId;

    private String creatorId;

    private String name;

    private String description;

    private CouponStatus status;

    private Long maxIssuableCount;

    private DateTimeInterval issueDateTime;

    private DateTimeInterval usableDateTime;

    @Builder
    public CouponResponse(Long couponId, String workspaceId, String ticketId, String providerId,
        String creatorId, String name, String description, CouponStatus status,
        Long maxIssuableCount, DateTimeInterval issueDateTime, DateTimeInterval usableDateTime) {
        this.couponId = couponId;
        this.workspaceId = workspaceId;
        this.ticketId = ticketId;
        this.providerId = providerId;
        this.creatorId = creatorId;
        this.name = name;
        this.description = description;
        this.status = status;
        this.maxIssuableCount = maxIssuableCount;
        this.issueDateTime = issueDateTime;
        this.usableDateTime = usableDateTime;
    }

    public CouponResponse from(Coupon coupon) {
        return CouponResponse.builder()
            .couponId(coupon.getId())
            .workspaceId(coupon.getWorkspaceId())
            .ticketId(coupon.getTicketId())
            .providerId(coupon.getProviderId())
            .creatorId(coupon.getCreatorId())
            .name(coupon.getName())
            .description(coupon.getDescription())
            .status(coupon.getStatus())
            .maxIssuableCount(coupon.getMaxIssuableCount())
            .issueDateTime(coupon.getIssueDateTime())
            .usableDateTime(coupon.getUsableDateTime())
            .build();
    }

}
