package com.threedollar.service.coupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.threedollar.IntegrationTest;
import com.threedollar.domain.coupon.Coupon;
import com.threedollar.domain.coupon.CouponGroup;
import com.threedollar.domain.coupon.CouponTag;
import com.threedollar.domain.coupon.CouponTime;
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
        LocalDateTime issueStartTime = LocalDateTime.of(2025, 2, 5, 0, 0);
        LocalDateTime issueEndTime = LocalDateTime.of(2025, 2, 6, 1, 0);
        LocalDateTime availableStartTime = LocalDateTime.of(2025, 6, 25, 0, 0);
        LocalDateTime availableEndTime = LocalDateTime.of(2025, 6, 30, 1, 0);
        CouponTime couponTime = new CouponTime(issueStartTime, issueEndTime, availableStartTime, availableEndTime);
        String name = "쿠폰 이름";
        CouponTag couponTag = CouponTag.BOSS_EVENT;
        Long maxCount = 1L;
        String creatorId = "USER222";
        String providerId = "store-1";

        AddCouponRequest request = new AddCouponRequest(name, couponTag, couponTime, creatorId,
            maxCount);

        String workspaceId = "three-dollar-dev";
        CouponGroup couponGroup = CouponGroup.BOSS_STORE;

        // when
        couponService.addCoupon(workspaceId, couponGroup, providerId, request);

        // then
        List<Coupon> couponList = couponRepository.findAll();
        assertThat(couponList).hasSize(1);
        assertThat(couponList.get(0).getName()).isEqualTo(name);
        assertThat(couponList.get(0).getCouponTag()).isEqualTo(couponTag);
        assertThat(couponList.get(0).getMaxCount()).isEqualTo(maxCount);
        assertThat(couponList.get(0).getWorkspaceId()).isEqualTo(workspaceId);
        assertThat(couponList.get(0).getProviderId()).isEqualTo(providerId);
        assertEquals(couponList.get(0).getCouponTime().getIssueStartTime(), issueStartTime);
        assertEquals(couponList.get(0).getCouponTime().getIssueEndTime(), issueEndTime);

    }

}
