package com.threedollar.service;

import com.threedollar.domain.coupon.Coupon;
import com.threedollar.domain.coupon.CouponStatus;
import com.threedollar.domain.coupon.service.CouponCreateService;
import com.threedollar.service.coupon.CouponQueryService;
import com.threedollar.service.dto.CouponResponse;
import com.threedollar.service.dto.request.CouponCreateRequest;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CouponCreateFacadeService {

    private final CouponCreateService couponCreateService;
    private final CouponQueryService couponQueryService;

    public CouponResponse create(String workspaceId, String ticketId, CouponCreateRequest request) {
        Coupon coupon = couponCreateService.create(workspaceId, ticketId, request.getProviderId(),
            request.getCreatorId(), request.getName(), request.getDescription(),
            request.getMaxIssuableCount(), request.getIssueDateTime(), request.getUsageDateTime());
        return new CouponResponse().from(coupon);

    }

    public CouponResponse getCouponById(String workspaceId, String ticketId, Long couponId) {
        Coupon coupon = couponQueryService.couponById(workspaceId, ticketId, couponId,
            List.of(CouponStatus.ACTIVE));
        return new CouponResponse().from(coupon);
    }


}
