package com.threedollar.service.coupon;

import com.threedollar.domain.coupon.Coupon;
import com.threedollar.domain.coupon.CouponStatus;
import com.threedollar.domain.coupon.IssueCoupon;
import com.threedollar.service.coupon.issuecoupon.IssueCouponQueryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.EnumSet;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CouponIssueFacadeServiceUseTest {

    @InjectMocks
    CouponIssueFacadeService couponIssueFacadeService;

    @Mock
    CouponQueryService couponQueryService;

    @Mock
    CouponUseCountService couponUseCountService;

    @Mock
    IssueCouponQueryService issueCouponQueryService;

    @Nested
    @DisplayName("쿠폰 사용")
    class Use {

        @Test
        @DisplayName("쿠폰이 사용 가능 기간이면 정상적으로 사용 처리 및 카운트 증가")
        void useSuccessWhenCouponUsable() {
            String workspaceId = "ws1";
            String ticketId = "t1";
            Long couponId = 10L;
            String ownerId = "owner1";

            IssueCoupon issueCoupon = mock(IssueCoupon.class);
            Coupon coupon = mock(Coupon.class);

            when(issueCouponQueryService.findIssueCoupon(workspaceId, ticketId, couponId, ownerId))
                .thenReturn(issueCoupon);
            when(issueCoupon.getCouponId()).thenReturn(couponId);
            when(couponQueryService.couponById(workspaceId, ticketId, couponId, EnumSet.of(CouponStatus.ACTIVE)))
                .thenReturn(coupon);
            when(coupon.isUsablePeriodValid(any(LocalDateTime.class))).thenReturn(true);

            couponIssueFacadeService.use(workspaceId, ticketId, couponId, ownerId);

            verify(issueCoupon).use();
            verify(couponUseCountService).incrCouponUseCount(workspaceId, ticketId, couponId);
        }

        @Test
        @DisplayName("쿠폰이 사용 가능 기간이 아니면 예외를 발생시키고 사용/카운트 증가가 진행되지 않는다")
        void throwExceptionWhenCouponNotUsable() {
            String workspaceId = "ws2";
            String ticketId = "t2";
            Long couponId = 20L;
            String ownerId = "owner2";

            IssueCoupon issueCoupon = mock(IssueCoupon.class);
            Coupon coupon = mock(Coupon.class);

            when(issueCouponQueryService.findIssueCoupon(workspaceId, ticketId, couponId, ownerId))
                .thenReturn(issueCoupon);
            when(issueCoupon.getCouponId()).thenReturn(couponId);
            when(couponQueryService.couponById(workspaceId, ticketId, couponId, EnumSet.of(CouponStatus.ACTIVE)))
                .thenReturn(coupon);
            when(coupon.isUsablePeriodValid(any(LocalDateTime.class))).thenReturn(false);

            assertThatThrownBy(() -> couponIssueFacadeService.use(workspaceId, ticketId, couponId, ownerId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("현재 시점에서 사용 가능한 쿠폰이 아닙니다.");

            verify(issueCoupon, never()).use();
            verifyNoInteractions(couponUseCountService);
        }
    }
}
