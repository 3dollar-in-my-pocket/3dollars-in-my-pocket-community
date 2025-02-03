package com.threedollar.service.coupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.threedollar.IntegrationTest;
import com.threedollar.domain.coupon.Coupon;
import com.threedollar.domain.coupon.CouponGroup;
import com.threedollar.domain.coupon.CouponTag;
import com.threedollar.domain.coupon.CouponTime;
import com.threedollar.domain.coupon.CouponType;
import com.threedollar.domain.coupon.repository.CouponRepository;
import com.threedollar.service.coupon.dto.request.AddCouponRequest;

import java.time.LocalDateTime;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CouponServiceTest extends IntegrationTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;

    @AfterEach
    void cleanUp() {
        couponRepository.deleteAll();
    }

    @Test
    void 쿠폰을_추가합니다() {
        // given
        LocalDateTime startTime = LocalDateTime.of(2025, 2, 5, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 2, 6, 1, 0);
        CouponTime couponTime = new CouponTime(startTime, endTime);
        String name = "쿠폰 이름";
        CouponType couponType = CouponType.LIMITED;
        CouponTag couponTag = CouponTag.BOSS_EVENT;
        Long count = 1L;
        String accountId = "USER222";

        AddCouponRequest request = new AddCouponRequest(name, couponType, couponTag, couponTime, count, accountId);
        String workspaceId = "three-dollar-dev";
        CouponGroup couponGroup = CouponGroup.BOSS_STORE;
        String targetId = "1";

        // when
        couponService.addCoupon(workspaceId, couponGroup, targetId, request);

        // then
        List<Coupon> couponList = couponRepository.findAll();
        assertThat(couponList).hasSize(1);
        assertThat(couponList.get(0).getName()).isEqualTo(name);
        assertThat(couponList.get(0).getCouponType()).isEqualTo(couponType);
        assertThat(couponList.get(0).getCouponTag()).isEqualTo(couponTag);
        assertThat(couponList.get(0).getCount()).isEqualTo(count);
        assertThat(couponList.get(0).getAccountId()).isEqualTo(accountId);
        assertThat(couponList.get(0).getWorkspaceId()).isEqualTo(workspaceId);
        assertThat(couponList.get(0).getTargetId()).isEqualTo(targetId);
        assertEquals(couponList.get(0).getCouponTime().getStartTime(), startTime);
        assertEquals(couponList.get(0).getCouponTime().getEndTime(), endTime);

    }

}
