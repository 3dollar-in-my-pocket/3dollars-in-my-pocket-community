package com.threedollar.domain.coupon.repository;

import static com.threedollar.domain.coupon.QCoupon.coupon;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.threedollar.domain.coupon.Coupon;
import com.threedollar.domain.coupon.CouponStatus;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CouponRepositoryCustomImpl implements CouponRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Set<Long> findExistingIds(String workspaceId, String ticketId, Set<Long> couponIds, Collection<CouponStatus> status) {
        if (couponIds == null || couponIds.isEmpty()) {
            return Collections.emptySet();
        }

        return new HashSet<>(
            jpaQueryFactory.select(coupon.id)
                .from(coupon)
                .where(
                    coupon.workspaceId.eq(workspaceId),
                    coupon.ticketId.eq(ticketId),
                    coupon.id.in(couponIds),
                    coupon.status.in(status)
                )
                .fetch()
        );
    }

    @Override
    public List<Coupon> findByCouponInfoWithLimit(String workspaceId, String ticketId,
        String providerId, Collection<CouponStatus> status, int size) {
        return jpaQueryFactory.selectFrom(coupon)
            .where(
                coupon.workspaceId.eq(workspaceId),
                coupon.ticketId.eq(ticketId),
                coupon.providerId.eq(providerId),
                coupon.status.in(status)
            )
            .orderBy(coupon.id.desc())
            .limit(size)
            .fetch();
    }
}
