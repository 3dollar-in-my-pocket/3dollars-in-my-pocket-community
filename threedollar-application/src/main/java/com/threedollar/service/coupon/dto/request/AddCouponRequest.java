package com.threedollar.service.coupon.dto.request;

import com.threedollar.domain.coupon.Coupon;
import com.threedollar.domain.coupon.CouponGroup;
import com.threedollar.domain.coupon.CouponTag;
import com.threedollar.domain.coupon.CouponTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@NoArgsConstructor
@Getter
public class AddCouponRequest {

    @NotBlank
    private String name;

    @NotNull
    private CouponTag couponTag;

    private CouponTime couponTime;

    private String providerId;

    private String creatorId;

    @Nullable
    private Long limitCount;

    public AddCouponRequest(String name, CouponTag couponTag, CouponTime couponTime,
        String providerId, String creatorId, @Nullable Long limitCount) {
        this.name = name;
        this.couponTag = couponTag;
        this.couponTime = couponTime;
        this.providerId = providerId;
        this.creatorId = creatorId;
        this.limitCount = limitCount;
    }

    public Coupon toEntity(String workspaceId, String providerId, String creatorId, CouponGroup couponGroup) {
        return Coupon.newInstance(workspaceId, providerId, creatorId, name, couponTag,
            couponGroup, limitCount, couponTime);
    }
}
