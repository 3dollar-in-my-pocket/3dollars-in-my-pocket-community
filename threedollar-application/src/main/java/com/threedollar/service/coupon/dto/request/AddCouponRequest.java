package com.threedollar.service.coupon.dto.request;

import com.threedollar.domain.coupon.Coupon;
import com.threedollar.domain.coupon.CouponGroup;
import com.threedollar.domain.coupon.CouponTag;
import com.threedollar.domain.coupon.CouponTime;
import com.threedollar.domain.coupon.CouponType;
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
    private CouponType couponType;

    @NotNull
    private CouponTag couponTag;

    private CouponTime couponTime;

    @Nullable
    private Long count;

    @NotBlank
    private String accountId;

    public AddCouponRequest(String name, CouponType couponType, CouponTag couponTag,
        CouponTime couponTime, @Nullable Long count, String accountId) {
        this.name = name;
        this.couponType = couponType;
        this.couponTag = couponTag;
        this.couponTime = couponTime;
        this.count = count;
        this.accountId = accountId;
    }

    public Coupon toEntity(String workspaceId, CouponGroup couponGroup, String targetId) {
        return Coupon.newInstance(workspaceId, targetId, name, couponType, couponTag,
            couponGroup, getCouponCount(couponType, count), couponTime, accountId);
    }

    private Long getCouponCount(CouponType couponType, @Nullable Long count) {
        if (couponType == CouponType.UNLIMITED || count == null) {
            return null;
        }
        return count;
    }
}
