package com.threedollar.controller.coupon;

import com.threedollar.common.dto.response.ApiResponse;
import com.threedollar.config.interceptor.ApiKeyContext;
import com.threedollar.config.resolver.RequestApiKey;
import com.threedollar.domain.coupon.CouponGroup;
import com.threedollar.domain.coupon.CouponUsageStatus;
import com.threedollar.service.usercoupon.dto.response.UserCouponAndCursorResponse;
import com.threedollar.service.usercoupon.dto.response.UserCouponService;

import io.swagger.v3.oas.annotations.Operation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserCouponController {

    private final UserCouponService userCouponService;

    @GetMapping("/v1/coupon-group/{couponGroup}/coupons")
    @Operation(summary = "[사용자 쿠폰] 유저가 발급 받은 쿠폰을 조회합니다.")
    public ApiResponse<UserCouponAndCursorResponse> userCoupons(
        @RequestApiKey ApiKeyContext workspaceId,
        @PathVariable CouponGroup couponGroup,
        @RequestParam String accountId,
        @RequestParam CouponUsageStatus couponUsageStatus,
        @RequestParam(required = false) Long cursor,
        @RequestParam int size) {
        return ApiResponse.success(userCouponService.getUserCouponList(workspaceId.getWorkspaceId(), couponGroup,
            accountId, couponUsageStatus, cursor, size));
    }

    @PostMapping("/v1/coupon-group/{couponGroup}/coupons/{couponId}")
    @Operation(summary = "[사용자 쿠폰] 사용자가 쿠폰을 발급 받습니다.")
    public ApiResponse<String> issueCoupon(@PathVariable CouponGroup couponGroup,
    @PathVariable Long couponId,
    @RequestApiKey ApiKeyContext workspaceId,
    @RequestParam String accountId) {
        userCouponService.issueCoupon(workspaceId.getWorkspaceId(), couponGroup, accountId, couponId);
        return ApiResponse.OK;
    }

    @PostMapping("/v1/coupon-group/{couponGroup}/coupons/{couponId}/use")
    @Operation(summary = "[사용자 쿠폰] 사용자가 쿠폰을 사용합니다")
    public ApiResponse<String> useCoupon(@PathVariable CouponGroup couponGroup,
        @PathVariable Long couponId,
        @RequestApiKey ApiKeyContext workspaceId,
        @RequestParam String accountId) {
        userCouponService.useCoupon(workspaceId.getWorkspaceId(), couponGroup, accountId, couponId);
        return ApiResponse.OK;
    }


}
