package com.threedollar.service.coupon;

import com.threedollar.domain.coupon.CouponStatus;
import com.threedollar.service.coupon.dto.response.CouponExistenceResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CouponCreateFacadeServiceTest {

    @Mock
    CouponQueryService couponQueryService;

    @InjectMocks
    CouponCreateFacadeService couponCreateFacadeService;

    @Test
    void checkCouponsExistence_allIdsExist() {
        Set<Long> ids = Set.of(1L, 2L, 3L);
        when(couponQueryService.findExistingCouponIds("ws", "t", ids, EnumSet.of(CouponStatus.ACTIVE)))
            .thenReturn(ids);

        List<CouponExistenceResponse> result = couponCreateFacadeService.checkCouponsExistence("ws", "t", ids);

        assertThat(result).hasSize(3);
        result.forEach(r -> assertThat(isExists(r)).isTrue());
        verify(couponQueryService).findExistingCouponIds("ws", "t", ids, EnumSet.of(CouponStatus.ACTIVE));
    }

    @Test
    void checkCouponsExistence_someIdsMissing() {
        Set<Long> ids = Set.of(1L, 2L, 3L, 4L);
        when(couponQueryService.findExistingCouponIds("ws", "t", ids, EnumSet.of(CouponStatus.ACTIVE)))
            .thenReturn(Set.of(1L, 3L));

        List<CouponExistenceResponse> result = couponCreateFacadeService.checkCouponsExistence("ws", "t", ids);

        assertThat(result).hasSize(4);
        assertThat(isExists(findById(result, 1L))).isTrue();
        assertThat(isExists(findById(result, 3L))).isTrue();
        assertThat(isExists(findById(result, 2L))).isFalse();
        assertThat(isExists(findById(result, 4L))).isFalse();
    }

    @Test
    void checkCouponsExistence_noIdsExist() {
        Set<Long> ids = Set.of(10L, 20L);
        when(couponQueryService.findExistingCouponIds("ws", "t", ids, EnumSet.of(CouponStatus.ACTIVE)))
            .thenReturn(Set.of());

        List<CouponExistenceResponse> result = couponCreateFacadeService.checkCouponsExistence("ws", "t", ids);

        assertThat(result).hasSize(2);
        result.forEach(r -> assertThat(isExists(r)).isFalse());
    }

    @Test
    void checkCouponsExistence_emptyInputReturnsEmptyList() {
        Set<Long> ids = Set.of();
        when(couponQueryService.findExistingCouponIds("ws", "t", ids, EnumSet.of(CouponStatus.ACTIVE)))
            .thenReturn(Set.of());

        List<CouponExistenceResponse> result = couponCreateFacadeService.checkCouponsExistence("ws", "t", ids);

        assertThat(result).isEmpty();
        verify(couponQueryService).findExistingCouponIds("ws", "t", ids, EnumSet.of(CouponStatus.ACTIVE));
    }

    @Test
    void checkCouponsExistence_nullSetThrowsNpe() {
        assertThatThrownBy(() -> couponCreateFacadeService.checkCouponsExistence("ws", "t", null))
            .isInstanceOf(NullPointerException.class);
        verifyNoInteractions(couponQueryService);
    }

    @Test
    void checkCouponsExistence_doesNotMutateInputSet() {
        Set<Long> ids = Set.of(5L, 6L);
        when(couponQueryService.findExistingCouponIds("ws", "t", ids, EnumSet.of(CouponStatus.ACTIVE)))
            .thenReturn(Set.of(5L));

        couponCreateFacadeService.checkCouponsExistence("ws", "t", ids);

        ArgumentCaptor<Set<Long>> captor = ArgumentCaptor.forClass(Set.class);
        verify(couponQueryService).findExistingCouponIds(eq("ws"), eq("t"), captor.capture(), eq(EnumSet.of(CouponStatus.ACTIVE)));
        assertThat(captor.getValue()).isUnmodifiable();
    }

    private CouponExistenceResponse findById(List<CouponExistenceResponse> list, Long id) {
        return list.stream()
            .filter(r -> id.equals(r.getCouponId()))
            .findFirst()
            .orElseThrow();
    }

    private boolean isExists(CouponExistenceResponse r) {
        return r.isExists(); // 필요시 r.getExists() 로 변경
    }

}
