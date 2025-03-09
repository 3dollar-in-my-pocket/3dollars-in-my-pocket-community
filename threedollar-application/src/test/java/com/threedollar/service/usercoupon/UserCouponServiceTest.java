package com.threedollar.service.usercoupon;


import com.threedollar.IntegrationTest;
import com.threedollar.domain.coupon.Coupon;
import com.threedollar.domain.coupon.CouponGroup;
import com.threedollar.domain.coupon.CouponTag;
import com.threedollar.domain.coupon.CouponTime;
import com.threedollar.domain.coupon.CouponType;
import com.threedollar.domain.coupon.repository.CouponRepository;
import com.threedollar.domain.coupon.repository.UserCouponRepository;
import com.threedollar.domain.coupon.usercoupon.UserCoupon;
import com.threedollar.service.usercoupon.dto.response.UserCouponService;

import java.time.LocalDateTime;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserCouponServiceTest extends IntegrationTest {

    @Autowired
    private UserCouponService userCouponService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    @AfterEach
    void clean_up() {
        userCouponRepository.deleteAll();
        couponRepository.deleteAll();
    }

    @Test
    void 유저가_쿠폰을_발급_받는다() {
        // given
        String workspaceId = "threedollar-test";
        CouponGroup couponGroup = CouponGroup.EVENT;
        String accountId = "USER1";
        Coupon coupon = getCoupon();

        // when
        userCouponService.issueCoupon(workspaceId, couponGroup, accountId, coupon.getId());

        // then
        List<UserCoupon> userCoupons = userCouponRepository.findAll();
        assertThat(userCoupons).hasSize(1);
        assertThat(userCoupons.get(0).getCouponId()).isEqualTo(coupon.getId());
        assertThat(userCoupons.get(0).getAccountId()).isEqualTo(accountId);

    }

    private Coupon getCoupon() {
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

        Coupon coupon = Coupon.newInstance(workspaceId, targetId, name, couponType, couponTag,
            couponGroup,
            count,
            couponTime,
            accountId);

        return couponRepository.save(coupon);

    }



}
