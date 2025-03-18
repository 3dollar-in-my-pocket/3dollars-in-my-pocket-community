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
        assertThat(coupon.getCount()).isEqualTo(0);

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
        String targetId = "USER1";
        String name = "테스트 쿠폰";
        CouponType couponType = CouponType.LIMITED;
        CouponTag couponTag = CouponTag.BOSS_EVENT;
        CouponGroup couponGroup = CouponGroup.BOSS_STORE;
        Long count = 1L;
        String accountId = "1";
        CouponTime couponTime = new CouponTime(
            LocalDateTime.of(2025, 3,9,0,0,0),
            LocalDateTime.of(2099, 3, 10, 0,0,0)
        );

        return Coupon.newInstance(workspaceId, targetId, name, couponType, couponTag,
            couponGroup,
            count,
            couponTime,
            accountId);

    }

    private Coupon getInvalidCoupon() {
        String workspaceId = "threedollar-test";
        String targetId = "USER1";
        String name = "테스트 쿠폰";
        CouponType couponType = CouponType.LIMITED;
        CouponTag couponTag = CouponTag.BOSS_EVENT;
        CouponGroup couponGroup = CouponGroup.BOSS_STORE;
        Long count = 1L;
        String accountId = "1";
        CouponTime couponTime = new CouponTime(
            LocalDateTime.of(2025, 3,9,0,0,0),
            LocalDateTime.of(2025, 3, 10, 0,0,0)
        );

        return Coupon.newInstance(workspaceId, targetId, name, couponType, couponTag,
            couponGroup,
            count,
            couponTime,
            accountId);

    }
}
