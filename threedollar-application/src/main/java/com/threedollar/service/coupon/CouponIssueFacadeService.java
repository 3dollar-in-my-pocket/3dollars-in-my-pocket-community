package com.threedollar.service.coupon;

import com.threedollar.controller.coupon.dto.request.CouponIssueRequest;
import com.threedollar.domain.coupon.Coupon;
import com.threedollar.domain.coupon.CouponStatus;

import com.threedollar.domain.coupon.IssueCoupon;
import com.threedollar.service.coupon.issuecoupon.IssueCouponCountService;
import com.threedollar.service.coupon.issuecoupon.IssueCouponQueryService;
import com.threedollar.service.coupon.event.CouponQuotaCompletedEvent;

import java.time.LocalDateTime;
import java.util.EnumSet;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class CouponIssueFacadeService {

    private final CouponIssueService couponIssueService;

    private final CouponQueryService couponQueryService;

    private final CouponUseCountService couponUseCountService;

    private final IssueCouponQueryService issueCouponQueryService;

    private final IssueCouponCountService issueCouponCountService;

    private final ApplicationEventPublisher eventPublisher;

    public void issue(String workspaceId, String ticketId, Long couponId,
        CouponIssueRequest request) {
        // 쿠폰 존재 여부 확인
        Coupon coupon = couponQueryService.couponById(workspaceId, ticketId, couponId, EnumSet.of(CouponStatus.ACTIVE));

        // 발급 하려는 대상에 해당 쿠폰이 존재하는지 확인
        boolean exists = issueCouponQueryService.existsIssueCoupon(workspaceId, ticketId, couponId, request.getOwnerId());
        if (exists) {
            throw new IllegalStateException("이미 발급된 쿠폰이 존재합니다.");
        }

        // 사용가능한지 확인 (시간)
        LocalDateTime now = LocalDateTime.now();
        if (!coupon.isUsablePeriodValid(now)) {
            throw new IllegalArgumentException("현재 시점에서 발급 가능한 쿠폰이 아닙니다.");
        };

        // 쿠폰 발급 가능 여부 확인 (쿼터)
        // Redis 로 발급 여부 확인
        long issuedCouponCount = issueCouponCountService.incrCouponCount(workspaceId, ticketId, couponId);
        if (coupon.getMaxIssuableCount() != null && issuedCouponCount > coupon.getMaxIssuableCount()) {
            throw new IllegalArgumentException(String.format(
                "workspaceId (%s), ticketId (%s), couponId (%s) 는 발급 가능한 최대 수량 (%s) 를 초과 하였습니다.",
                workspaceId, ticketId, coupon.getId(), coupon.getMaxIssuableCount()));
        }

        // 쿠폰 발급 처리
        couponIssueService.issueCoupon(workspaceId, ticketId, request.getOwnerId(), couponId, coupon.getUsableDateTime());

        // 쿠폰 상태 변경 이벤트 처리 (쿼터 소진 시 비동기 상태 전환)
        if (coupon.getMaxIssuableCount() != null && issuedCouponCount == coupon.getMaxIssuableCount()) {
            eventPublisher.publishEvent(new CouponQuotaCompletedEvent(coupon.getId(), workspaceId, ticketId, coupon.getMaxIssuableCount()));
        }
    }

    public void use(String workspaceId, String ticketId, Long couponId, String ownerId) {
        // 쿠폰 존재 여부 및 사용 처리
        IssueCoupon issueCoupon = issueCouponQueryService.findIssueCoupon(workspaceId, ticketId, couponId, ownerId);
        Coupon coupon = couponQueryService.couponById(workspaceId, ticketId, issueCoupon.getCouponId(), EnumSet.of(CouponStatus.ACTIVE));

        // 사용 가능 여부 확인
        if (!coupon.isUsablePeriodValid(LocalDateTime.now())) {
            throw new IllegalArgumentException("현재 시점에서 사용 가능한 쿠폰이 아닙니다.");
        }

        // 쿠폰 사용 처리
        issueCoupon.use();

        // 쿠폰 사용 건수 증가 처리
        couponUseCountService.incrCouponUseCount(workspaceId, ticketId, couponId);

    }

}
