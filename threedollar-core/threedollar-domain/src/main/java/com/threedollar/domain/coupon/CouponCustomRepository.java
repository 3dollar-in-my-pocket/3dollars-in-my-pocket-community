package com.threedollar.domain.coupon;

import java.util.Collection;
import java.util.Set;

public interface CouponCustomRepository {

    /**
     * 주어진 couponIds 중 실제 존재하며 조건을 만족하는 쿠폰 ID 집합 반환
     */
    Set<Long> findExistingIds(String workspaceId, String ticketId, Set<Long> couponIds, Collection<CouponStatus> status);
}

