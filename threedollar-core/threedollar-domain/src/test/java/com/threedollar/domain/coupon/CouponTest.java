package com.threedollar.domain.coupon;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class CouponTest {

    @Test
    void 쿠폰을_사용하면_수량이_한개_감소한다() {
        // given
        Coupon coupon = getValidCoupon();

        // when
        coupon.issueCoupon();

        // then
        assertThat(coupon.getLimitCount()).isEqualTo(0);

    }

    @Test
    void 유효하지_않은_쿠폰을_사용할_때_에러가_발생한다() {
        // given
        Coupon coupon = getInvalidCoupon();

        // when & then
        assertThatThrownBy(coupon::issueCoupon)
            .isInstanceOf(IllegalArgumentException.class);

    }

    private Coupon getValidCoupon() {
        String workspaceId = "threedollar-test";
        String providerId = "STORE-1";
        String creatorId = "USER2";
        String name = "테스트 쿠폰";
        CouponTag couponTag = CouponTag.BOSS_EVENT;
        CouponGroup couponGroup = CouponGroup.BOSS_STORE;
        Long maxCount = 1L;

        LocalDateTime issueStartTime = LocalDateTime.of(2025, 3,9,0,0,0);
        LocalDateTime issueEndTime = LocalDateTime.of(2025, 6, 25, 0, 0, 0);
        LocalDateTime availableStartTime = LocalDateTime.of(2025, 6, 20, 0, 0, 0);
        LocalDateTime availableEndTime = LocalDateTime.of(2025, 6, 30, 0, 0, 0);


        CouponTime couponTime = new CouponTime(
            issueStartTime,
            issueEndTime,
            availableStartTime,
            availableEndTime
        );


        return Coupon.newInstance(workspaceId, providerId, creatorId, name, couponTag,
            couponGroup,
            maxCount,
            couponTime);

    }

    private Coupon getInvalidCoupon() {
        String workspaceId = "threedollar-test";
        String providerId = "STORE-1";
        String creatorId = "USER2";
        String name = "테스트 쿠폰";
        CouponTag couponTag = CouponTag.BOSS_EVENT;
        CouponGroup couponGroup = CouponGroup.BOSS_STORE;
        Long count = 1L;
        String accountId = "1";


        LocalDateTime issueStartTime = LocalDateTime.of(2025, 3,9,0,0,0);
        LocalDateTime issueEndTime = LocalDateTime.of(2025, 6, 25, 0, 0, 0);
        LocalDateTime availableStartTime = LocalDateTime.of(2025, 6, 20, 0, 0, 0);
        LocalDateTime availableEndTime = LocalDateTime.of(2025, 6, 30, 0, 0, 0);


        CouponTime couponTime = new CouponTime(
            issueStartTime,
            issueEndTime,
            availableStartTime,
            availableEndTime

        );

        return Coupon.newInstance(workspaceId, providerId, creatorId, name, couponTag,
            couponGroup, count, couponTime);
    }
}
