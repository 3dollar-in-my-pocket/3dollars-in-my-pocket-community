package com.threedollar.domain.coupon.repository;

import static com.threedollar.domain.coupon.QCoupon.coupon;

import com.querydsl.jpa.impl.JPAQueryFactory;

import com.threedollar.domain.coupon.Coupon;

import com.threedollar.domain.coupon.CouponGroup;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CouponRepositoryCustomImpl implements CouponRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Coupon> findByCouponInfoAndSize(String workspaceId,
        CouponGroup couponGroup, String providerId, String creatorId, int size) {
        return jpaQueryFactory.selectFrom(coupon)
            .where(
                coupon.workspaceId.eq(workspaceId),
                coupon.providerId.eq(providerId),
                coupon.creatorId.eq(creatorId),
                coupon.couponGroup.eq(couponGroup)
            ).orderBy(coupon.id.desc())
            .limit(size)
            .fetch();
    }
}
