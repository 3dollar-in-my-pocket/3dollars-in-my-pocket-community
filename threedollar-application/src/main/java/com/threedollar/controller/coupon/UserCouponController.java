package com.threedollar.controller.coupon;

import com.threedollar.common.dto.response.ApiResponse;
import com.threedollar.config.interceptor.ApiKeyContext;
import com.threedollar.config.resolver.RequestApiKey;
import com.threedollar.domain.coupon.CouponGroup;
import com.threedollar.domain.coupon.CouponUsageStatus;
import com.threedollar.service.usercoupon.dto.response.UserCouponResponse;
import com.threedollar.service.usercoupon.dto.response.UserCouponService;

import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserCouponController {

    private final UserCouponService userCouponService;

    @GetMapping("/v1/coupon-group/{couponGroup}/coupons")
    @Operation(summary = "[사용자 쿠폰] 사용자가 보유한 쿠폰을 조회합니다.")
    public ApiResponse<List<UserCouponResponse>> userCoupons(
        @RequestApiKey ApiKeyContext workspaceId,
        @PathVariable CouponGroup couponGroup,
        @RequestParam String accountId,
        CouponUsageStatus couponUsageStatus) {
        return ApiResponse.success(userCouponService.getUserCouponList(workspaceId.getWorkspaceId(), couponGroup,
            accountId, couponUsageStatus));
    }

}
