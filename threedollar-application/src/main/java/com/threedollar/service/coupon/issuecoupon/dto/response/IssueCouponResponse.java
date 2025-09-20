package com.threedollar.service.coupon.issuecoupon.dto.response;

import com.threedollar.domain.coupon.DateTimeInterval;
import com.threedollar.domain.coupon.IssueCoupon;
import com.threedollar.domain.coupon.IssueCouponStatus;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class IssueCouponResponse {

    private String workspaceId;

    private String ticketId;

    private String ownerId;

    private Long couponId;

    private IssueCouponStatus status;

    private DateTimeInterval usableDateTime;

    private LocalDateTime issuedDateTime;


    @Builder(access = lombok.AccessLevel.PRIVATE)
    public IssueCouponResponse(String workspaceId, String ticketId, String ownerId, Long couponId,
        IssueCouponStatus status, DateTimeInterval usableDateTime, LocalDateTime issuedDateTime) {
        this.workspaceId = workspaceId;
        this.ticketId = ticketId;
        this.ownerId = ownerId;
        this.couponId = couponId;
        this.status = status;
        this.usableDateTime = usableDateTime;
        this.issuedDateTime = issuedDateTime;
    }

    public static IssueCouponResponse from(IssueCoupon issueCoupon) {
        return IssueCouponResponse.builder()
            .workspaceId(issueCoupon.getWorkspaceId())
            .ticketId(issueCoupon.getTicketId())
            .ownerId(issueCoupon.getOwnerId())
            .couponId(issueCoupon.getCouponId())
            .status(issueCoupon.getStatus())
            .usableDateTime(issueCoupon.getUsableDateTime())
            .issuedDateTime(issueCoupon.getIssuedDateTime())
            .build();
    }

}
