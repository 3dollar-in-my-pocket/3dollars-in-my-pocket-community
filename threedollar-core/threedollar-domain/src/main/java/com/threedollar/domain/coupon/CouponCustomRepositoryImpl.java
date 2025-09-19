package com.threedollar.domain.coupon;

import static com.threedollar.domain.coupon.QCoupon.coupon;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CouponCustomRepositoryImpl implements CouponCustomRepository {

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
}
