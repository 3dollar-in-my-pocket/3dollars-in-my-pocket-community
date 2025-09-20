package com.threedollar.service.coupon;

import com.threedollar.domain.coupon.Coupon;
import com.threedollar.domain.coupon.CouponStatus;
import com.threedollar.domain.coupon.service.CouponCreateService;
import com.threedollar.service.coupon.dto.response.CouponExistenceResponse;
import com.threedollar.service.coupon.dto.response.CouponResponse;
import com.threedollar.service.coupon.dto.request.CouponCreateRequest;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

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


    public List<CouponExistenceResponse> checkCouponsExistence(String workspaceId, String ticketId, Set<Long> couponIds) {
        Set<Long> existing = couponQueryService.findExistingCouponIds(workspaceId, ticketId, Set.copyOf(couponIds),
            EnumSet.of(CouponStatus.ACTIVE));

        return couponIds.stream()
            .map(id -> CouponExistenceResponse.of(id, existing.contains(id)))
            .toList();
    }
}
