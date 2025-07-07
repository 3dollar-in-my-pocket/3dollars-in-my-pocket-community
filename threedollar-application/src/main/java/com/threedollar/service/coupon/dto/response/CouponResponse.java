package com.threedollar.service.coupon.dto.response;

import com.threedollar.domain.coupon.Coupon;
import com.threedollar.domain.coupon.CouponGroup;
import com.threedollar.domain.coupon.CouponStatus;
import com.threedollar.domain.coupon.CouponTag;
import com.threedollar.domain.coupon.CouponTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CouponResponse {

    private Long couponId;

    private String providerId;

    private String name;

    private CouponTag couponTag;

    private CouponGroup couponGroup;

    private CouponStatus couponStatus;

    private Long limitCount;

    private CouponTime couponTime;

    private String creatorId;


    @Builder
    public CouponResponse(Long couponId, String providerId, String name,
        CouponTag couponTag, CouponGroup couponGroup,
        CouponStatus couponStatus,
        Long limitCount, CouponTime couponTime, String accountId,
        String creatorId) {
        this.couponId = couponId;
        this.providerId = providerId;
        this.name = name;
        this.couponTag = couponTag;
        this.couponGroup = couponGroup;
        this.couponStatus = couponStatus;
        this.limitCount = limitCount;
        this.couponTime = couponTime;
        this.creatorId = creatorId;
    }

    public static CouponResponse of(Coupon coupon) {
        return CouponResponse.builder()
            .couponId(coupon.getId())
            .providerId(coupon.getProviderId())
            .name(coupon.getName())
            .couponTag(coupon.getCouponTag())
            .couponGroup(coupon.getCouponGroup())
            .couponStatus(coupon.getStatus())
            .couponTime(coupon.getCouponTime())
            .creatorId(coupon.getCreatorId())
            .build();
    }

}
