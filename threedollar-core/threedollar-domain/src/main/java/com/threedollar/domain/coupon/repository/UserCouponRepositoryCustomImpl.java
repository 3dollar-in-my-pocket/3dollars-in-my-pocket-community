package com.threedollar.domain.coupon.repository;

import static com.threedollar.domain.coupon.usercoupon.QUserCoupon.userCoupon;
import com.querydsl.jpa.impl.JPAQueryFactory;

import com.threedollar.common.QuerydslUtil;
import com.threedollar.domain.coupon.CouponGroup;
import com.threedollar.domain.coupon.CouponUsageStatus;
import com.threedollar.domain.coupon.usercoupon.UserCoupon;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserCouponRepositoryCustomImpl implements UserCouponRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<UserCoupon> findUserCoupon(String workspaceId, CouponGroup couponGroup,
        String accountId, CouponUsageStatus usageStatus, Long cursor, int size) {
        return jpaQueryFactory.selectFrom(userCoupon)
            .where(
                QuerydslUtil.dynamicQuery(cursor != null, () -> userCoupon.id.lt(cursor)),
                userCoupon.workspaceId.eq(workspaceId),
                userCoupon.couponGroup.eq(couponGroup),
                userCoupon.accountId.eq(accountId),
                userCoupon.usageStatus.eq(usageStatus)
            )
            .orderBy(userCoupon.id.desc())
            .limit(size)
            .fetch();
    }

}
