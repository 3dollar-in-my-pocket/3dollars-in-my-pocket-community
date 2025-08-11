package com.threedollar.service.usercoupon;


import com.threedollar.IntegrationTest;
import com.threedollar.domain.coupon.Coupon;
import com.threedollar.domain.coupon.CouponGroup;
import com.threedollar.domain.coupon.CouponTag;
import com.threedollar.domain.coupon.CouponTime;
import com.threedollar.domain.coupon.CouponUsageStatus;
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
        CouponGroup couponGroup = CouponGroup.BOSS_STORE;
        String accountId = "USER1";
        Coupon coupon = getCoupon();

        // when
        userCouponService.issueCoupon(workspaceId, couponGroup, coupon.getId(), accountId);

        // then
        List<UserCoupon> userCoupons = userCouponRepository.findAll();
        assertThat(userCoupons).hasSize(1);
        assertThat(userCoupons.get(0).getCouponId()).isEqualTo(coupon.getId());
        assertThat(userCoupons.get(0).getAccountId()).isEqualTo(accountId);

        List<Coupon> coupons = couponRepository.findAll();
        assertThat(coupons).hasSize(1);
        assertThat(coupons.get(0).getCouponGroup()).isEqualTo(couponGroup);
        assertThat(coupons.get(0).getMaxCount()).isEqualTo(0);

    }

    @Test
    void 유저가_쿠폰을_사용한다() {
        // given
        String workspaceId = "threedollar-test";
        CouponGroup couponGroup = CouponGroup.BOSS_STORE;
        String accountId = "USER1";
        Coupon coupon = getCoupon();


        UserCoupon userCoupon = UserCoupon.newInstance(coupon.getId(), workspaceId, couponGroup,
            accountId, coupon.getCouponTime().getAvailableStartTime(), coupon.getCouponTime().getAvailableEndTime());
        userCouponRepository.save(userCoupon);

        // when
        userCouponService.useCoupon(workspaceId, couponGroup, accountId, coupon.getId());

        // then
        List<UserCoupon> userCoupons = userCouponRepository.findAll();
        assertThat(userCoupons).hasSize(1);
        assertThat(userCoupons.get(0).getCouponGroup()).isEqualTo(couponGroup);
        assertThat(userCoupons.get(0).getAccountId()).isEqualTo(accountId);
        assertThat(userCoupons.get(0).getWorkspaceId()).isEqualTo(workspaceId);
        assertThat(userCoupons.get(0).getUsageStatus()).isEqualTo(CouponUsageStatus.USED);

    }

    private Coupon getCoupon() {
        String workspaceId = "threedollar-test";
        String providerId = "USER1";

        String name = "테스트 쿠폰";
        CouponTag couponTag = CouponTag.BOSS_EVENT;
        CouponGroup couponGroup = CouponGroup.BOSS_STORE;
        Long count = 1L;
        String accountId = "1";

        LocalDateTime issueStartTime = LocalDateTime.of(2025, 3, 9, 0, 0, 0);
        LocalDateTime issueEndTime = LocalDateTime.now().plusDays(1);
        LocalDateTime availableStartTime = LocalDateTime.of(2025, 3, 9, 0, 0, 0);
        LocalDateTime availableEndTime = LocalDateTime.of(2099, 3, 20, 0, 0, 0);
        CouponTime couponTime = new CouponTime(
            issueStartTime,
            issueEndTime,
            availableStartTime,
            availableEndTime
        );

        Coupon coupon = Coupon.newInstance(workspaceId, providerId, accountId, name, couponTag,
            couponGroup,
            count,
            couponTime);

        return couponRepository.save(coupon);

    }



}
