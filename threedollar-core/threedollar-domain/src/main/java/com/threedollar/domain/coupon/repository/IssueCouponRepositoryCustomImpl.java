package com.threedollar.domain.coupon.repository;

import static com.threedollar.domain.coupon.QIssueCoupon.issueCoupon;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.threedollar.domain.coupon.IssueCoupon;
import com.threedollar.domain.coupon.IssueCouponStatus;

import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IssueCouponRepositoryCustomImpl implements IssueCouponRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<IssueCoupon> findIssueCouponsByOwnerWithLimit(String workspaceId, String ticketId,
        String ownerId, Set<IssueCouponStatus> status, int size) {
        return jpaQueryFactory.selectFrom(issueCoupon)
            .where(
                issueCoupon.workspaceId.eq(workspaceId),
                issueCoupon.ticketId.eq(ticketId),
                issueCoupon.ownerId.eq(ownerId),
                issueCoupon.status.in(status)
            )
            .orderBy(issueCoupon.id.desc())
            .limit(size)
            .fetch();
    }

}
