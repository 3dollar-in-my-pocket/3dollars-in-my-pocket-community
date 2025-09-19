package com.threedollar.service.coupon.dto.request;

import com.threedollar.domain.coupon.DateTimeInterval;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CouponCreateRequest {

    private String workspaceId;

    private String ticketId;

    private String providerId;

    private String creatorId;

    private String name;

    private String description;

    private DateTimeInterval issueDateTime;

    private DateTimeInterval usageDateTime;

    private Long maxIssuableCount;

    public CouponCreateRequest(String workspaceId, String ticketId, String providerId,
        String creatorId,
        String name, String description, DateTimeInterval issueDateTime,
        DateTimeInterval usageDateTime,
        Long maxIssuableCount) {
        this.workspaceId = workspaceId;
        this.ticketId = ticketId;
        this.providerId = providerId;
        this.creatorId = creatorId;
        this.name = name;
        this.description = description;
        this.issueDateTime = issueDateTime;
        this.usageDateTime = usageDateTime;
        this.maxIssuableCount = maxIssuableCount;
    }

}
