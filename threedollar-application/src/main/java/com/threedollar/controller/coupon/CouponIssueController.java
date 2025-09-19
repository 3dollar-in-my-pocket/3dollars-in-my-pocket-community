package com.threedollar.controller.coupon;

import com.threedollar.common.dto.response.ApiResponse;
import com.threedollar.config.interceptor.ApiKeyContext;
import com.threedollar.config.resolver.RequestApiKey;
import com.threedollar.controller.coupon.dto.CouponIssueRequest;
import com.threedollar.service.coupon.CouponIssueFacadeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CouponIssueController {

    private final CouponIssueFacadeService couponIssueFacadeService;

    @PostMapping("/v1/ticket/{ticketId}/coupon/{couponId}/issue")
    @Operation(summary = "쿠폰을 발급 받습니다.")
    public ApiResponse<String> issue(
        @RequestApiKey ApiKeyContext authContext,
        @PathVariable String ticketId,
        @PathVariable Long couponId,
        @Valid @RequestBody CouponIssueRequest request
    ) {
        couponIssueFacadeService.issue(authContext.getWorkspaceId(), ticketId, couponId, request);
        return ApiResponse.OK;
    }

}
