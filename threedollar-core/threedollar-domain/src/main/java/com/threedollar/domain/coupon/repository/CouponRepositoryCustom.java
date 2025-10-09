package com.threedollar.domain.coupon.repository;

import com.threedollar.domain.coupon.Coupon;
import com.threedollar.domain.coupon.CouponStatus;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface CouponRepositoryCustom {

    /**
     * 주어진 couponIds 중 실제 존재하며 조건을 만족하는 쿠폰 ID 집합 반환
     */
    Set<Long> findExistingIds(String workspaceId, String ticketId, Set<Long> couponIds, Collection<CouponStatus> status);

    List<Coupon> findByCouponInfoWithLimit(String workspaceId, String ticketId, String providerId, Collection<CouponStatus> status, int size);

}

