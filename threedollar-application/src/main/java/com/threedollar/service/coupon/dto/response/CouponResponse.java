package com.threedollar.service.coupon.dto.response;

import com.threedollar.domain.coupon.Coupon;
import com.threedollar.domain.coupon.CouponGroup;
import com.threedollar.domain.coupon.CouponStatus;
import com.threedollar.domain.coupon.CouponTag;
import com.threedollar.domain.coupon.CouponTime;
import com.threedollar.domain.coupon.CouponType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CouponResponse {

    private Long couponId;

    private String targetId;

    private String name;

    private CouponTag couponTag;

    private CouponType couponType;

    private CouponGroup couponGroup;

    private CouponStatus couponStatus;

    private Long count;

    private CouponTime couponTime;

    private String accountId;

    @Builder
    public CouponResponse(Long couponId, String targetId, String name,
        CouponTag couponTag, CouponType couponType, CouponGroup couponGroup,
        CouponStatus couponStatus,
        Long count, CouponTime couponTime, String accountId) {
        this.couponId = couponId;
        this.targetId = targetId;
        this.name = name;
        this.couponTag = couponTag;
        this.couponType = couponType;
        this.couponGroup = couponGroup;
        this.couponStatus = couponStatus;
        this.count = count;
        this.couponTime = couponTime;
        this.accountId = accountId;
    }

    public static CouponResponse of(Coupon coupon) {
        return CouponResponse.builder()
            .couponId(coupon.getId())
            .targetId(coupon.getTargetId())
            .name(coupon.getName())
            .couponTag(coupon.getCouponTag())
            .couponType(coupon.getCouponType())
            .couponGroup(coupon.getCouponGroup())
            .couponStatus(coupon.getStatus())
            .count(getCouponCount(coupon))
            .couponTime(coupon.getCouponTime())
            .accountId(coupon.getAccountId())
            .build();
    }

    private static Long getCouponCount(Coupon coupon) {
        if (coupon.getCouponType() == CouponType.LIMITED) {
            return coupon.getCount();
        }
        return null;
    }

}
