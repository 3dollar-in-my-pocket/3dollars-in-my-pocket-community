package com.threedollar.domain.coupon.repository;

import com.threedollar.domain.coupon.IssueCoupon;

import com.threedollar.domain.coupon.IssueCouponStatus;

import java.util.List;
import java.util.Set;

public interface IssueCouponRepositoryCustom {

    List<IssueCoupon> findIssueCouponsByOwnerWithLimit(String workspaceId,
        String ticketId, String ownerId, Set<IssueCouponStatus> status, int size);

}
