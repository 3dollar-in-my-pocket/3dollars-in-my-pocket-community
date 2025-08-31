package com.threedollar.controller.coupon;

import com.threedollar.common.dto.response.ApiResponse;
import com.threedollar.config.interceptor.ApiKeyContext;
import com.threedollar.config.resolver.RequestApiKey;
import com.threedollar.domain.coupon.CouponGroup;
import com.threedollar.service.coupon.CouponService;
import com.threedollar.service.coupon.dto.request.AddCouponRequest;
import com.threedollar.service.coupon.dto.response.CouponAndCursorResponse;

import com.threedollar.service.coupon.dto.response.CouponExistence;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @GetMapping("/v1/coupon-group/{couponGroup}/provider/{providerId}")
    @Operation(summary = "[쿠폰] 사장님(유저)이 발급한 쿠폰들을 조회합니다.")
    public ApiResponse<CouponAndCursorResponse> couponAndCursorResponseApiResponse(
        @RequestApiKey ApiKeyContext workspaceId,
        @PathVariable CouponGroup couponGroup,
        @PathVariable String providerId,
        @RequestParam(defaultValue = "20", required = false) Integer size,
        @RequestParam(required = false) String creatorId) {
        return ApiResponse.success(couponService.getCoupons(workspaceId.getWorkspaceId(), couponGroup,
            providerId, creatorId, size));

    }

    @PostMapping("/v1/coupon-group/{couponGroup}/provider/{provider}")
    @Operation(summary = "[쿠폰] 사장님(유저)가 쿠폰을 발급합니다.")
    public ApiResponse<String> couponResponse(@RequestApiKey ApiKeyContext workspaceId,
        @PathVariable CouponGroup couponGroup, @PathVariable String provider,
        @Valid @RequestBody AddCouponRequest addCouponRequest) {
        couponService.addCoupon(workspaceId.getWorkspaceId(), couponGroup, provider, addCouponRequest);
        return ApiResponse.OK;
    }

    @GetMapping("/v1/coupon-group/{couponGroup}/provider-existence")
    @Operation(summary = "[쿠폰] 사장님(유저)가 쿠폰을 발급했는지 확인합니다.",
        description = "쿠폰에 대해 사장님(유저)가 발급한 쿠폰이 있는지 확인합니다. " +
            "쿠폰 그룹과 사장님(유저)의 ID를 통해 확인할 수 있습니다.")
    public ApiResponse<List<CouponExistence>> existsCouponByProvider(
        @RequestApiKey ApiKeyContext workspaceId,
        @PathVariable CouponGroup couponGroup,
        @RequestParam Set<String> providerId) {
        List<CouponExistence> coupon = couponService.existsCouponByProvider(workspaceId.getWorkspaceId(),
            couponGroup, providerId, LocalDateTime.now());
        return ApiResponse.success(coupon);
    }

}
