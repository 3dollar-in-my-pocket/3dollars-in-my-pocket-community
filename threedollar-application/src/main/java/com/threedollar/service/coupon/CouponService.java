package com.threedollar.service.coupon;

import com.threedollar.common.exception.ConflictException;
import com.threedollar.domain.coupon.Coupon;
import com.threedollar.domain.coupon.CouponGroup;
import com.threedollar.domain.coupon.repository.CouponRepository;
import com.threedollar.domain.coupon.repository.redis.couponissuecount.CouponIssueCountKey;
import com.threedollar.domain.coupon.repository.redis.couponissuecount.CouponIssueCountRepository;
import com.threedollar.service.coupon.dto.request.AddCouponRequest;
import com.threedollar.service.coupon.dto.response.CouponAndCursorResponse;
import com.threedollar.service.coupon.dto.response.CouponExistence;
import com.threedollar.service.coupon.dto.response.CouponResponse;

import java.time.LocalDateTime;
import java.util.List;

import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private static final int MAX_VALID_COUPON_COUNT = 5;

    private final CouponRepository couponRepository;

    private final CouponIssueCountRepository couponIssueCountRepository;

    @Transactional(readOnly = true)
    public CouponAndCursorResponse getCoupons(String workspaceId, CouponGroup couponGroup,
        String providerId, String creatorId, int size) {
        List<CouponResponse> couponList = couponRepository.findByCouponInfoAndSize(
                workspaceId, couponGroup, providerId, creatorId, size + 1)
            .stream()
            .map(CouponResponse::of)
            .toList();

        if (couponList.isEmpty() || couponList.size() <= size) {
            return CouponAndCursorResponse.noMore(couponList);
        }
        return CouponAndCursorResponse.hasNext(couponList);
    }


    @Transactional
    public void addCoupon(String workspaceId, CouponGroup couponGroup, String providerId,
        AddCouponRequest request) {
        List<Coupon> validCoupons = couponRepository.findValidCouponByProviderInfo(workspaceId,
            couponGroup, providerId, LocalDateTime.now());

        // 발급한 유효한 쿠폰이 5개 이상인 경우, 발급 불가
        if (validCoupons.size() >= 5) {
            throw new ConflictException(String.format("유효한 쿠폰은 쿠폰은 최대 %s 개까지 생성할 수 있습니다.",
                MAX_VALID_COUPON_COUNT));
        }

        // 쿠폰 생성
        Coupon coupon = request.toEntity(workspaceId, providerId, request.getCreatorId(),
            couponGroup);
        couponRepository.save(coupon);

        // 발급한 쿠폰 수 증가
        couponIssueCountRepository.incrByCount(coupon.getId(), workspaceId, providerId);
    }

    @Transactional
    public List<CouponExistence> existsCouponByProvider(String workspaceId,
        CouponGroup couponGroup,
        Set<String> providerIds, LocalDateTime now) {
        return providerIds.stream()
            .map(providerId -> {
                List<Coupon> coupons = couponRepository.findValidCouponByProviderInfo(
                    workspaceId, couponGroup, providerId, now);
                
                if (coupons.isEmpty()) {
                    return new CouponExistence(providerId, false);
                }

                long usedCount = coupons.stream()
                    .mapToLong(coupon -> couponIssueCountRepository.getValueByKey(
                        CouponIssueCountKey.of(coupon.getId(), workspaceId, providerId)))
                    .sum();
                // 발급할 수 있는 쿠폰이 있는지 확인
                boolean exists = coupons.stream()
                    .anyMatch(coupon -> coupon.getMaxCount() > usedCount);

                return new CouponExistence(providerId, exists);
            })
            .toList();
    }


}
