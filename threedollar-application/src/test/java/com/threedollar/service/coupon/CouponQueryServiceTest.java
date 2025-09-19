package com.threedollar.service.coupon;

import com.threedollar.domain.coupon.Coupon;
import com.threedollar.domain.coupon.CouponStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.EnumSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CouponIssueFacadeServiceTest {

    @Mock
    CouponQueryService couponQueryService;


    @Nested
    @DisplayName("쿠폰 조회")
    class CouponById {

        @Test
        @DisplayName("쿠폰이 ACTIVE 상태일 때 정상적으로 조회된다")
        void returnsActiveCoupon() {
            String workspaceId = "ws1";
            String ticketId = "t1";
            Long couponId = 1L;
            Coupon coupon = mock(Coupon.class);

            when(couponQueryService.couponById(workspaceId, ticketId, couponId, EnumSet.of(CouponStatus.ACTIVE)))
                .thenReturn(coupon);

            Coupon result = couponQueryService.couponById(workspaceId, ticketId, couponId, EnumSet.of(CouponStatus.ACTIVE));

            assertThat(result).isEqualTo(coupon);
        }

        @Test
        @DisplayName("쿠폰이 존재하지 않을 때 null을 반환한다")
        void returnsNullWhenCouponNotFound() {
            String workspaceId = "ws2";
            String ticketId = "t2";
            Long couponId = 2L;

            when(couponQueryService.couponById(workspaceId, ticketId, couponId, EnumSet.of(CouponStatus.ACTIVE)))
                .thenReturn(null);

            Coupon result = couponQueryService.couponById(workspaceId, ticketId, couponId, EnumSet.of(CouponStatus.ACTIVE));

            assertThat(result).isNull();
        }

        @Test
        @DisplayName("쿠폰이 ACTIVE 상태가 아닐 때 null을 반환한다")
        void returnsNullWhenCouponNotActive() {
            String workspaceId = "ws3";
            String ticketId = "t3";
            Long couponId = 3L;

            when(couponQueryService.couponById(workspaceId, ticketId, couponId, EnumSet.of(CouponStatus.ACTIVE)))
                .thenReturn(null);

            Coupon result = couponQueryService.couponById(workspaceId, ticketId, couponId, EnumSet.of(CouponStatus.ACTIVE));

            assertThat(result).isNull();
        }
    }
}
