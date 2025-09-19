package com.threedollar.controller.coupon;

import com.threedollar.common.dto.response.ApiResponse;
import com.threedollar.config.interceptor.ApiKeyContext;
import com.threedollar.config.resolver.RequestApiKey;
import com.threedollar.service.CouponCreateFacadeService;
import com.threedollar.service.coupon.dto.response.CouponExistenceResponse;
import com.threedollar.service.coupon.dto.response.CouponResponse;
import com.threedollar.service.coupon.dto.request.CouponCreateRequest;
import com.threedollar.service.coupon.dto.request.CouponExistenceBulkRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "Coupon API")
public class CouponController {

    private final CouponCreateFacadeService couponCreateFacadeService;

    @PostMapping("/v1/ticket/{ticketId}/coupon")
    @Operation(summary = "쿠폰 생성")
    public ApiResponse<String> create(@PathVariable String ticketId,
        @RequestApiKey ApiKeyContext authContext,
        @Valid @RequestBody CouponCreateRequest request) {
        couponCreateFacadeService.create(authContext.getWorkspaceId(), ticketId, request);
        return ApiResponse.OK;
    }

    @GetMapping("/v1/ticket/{ticketId}/coupon/{couponId}")
    @Operation(summary = "쿠폰 단건 조회")
    public ApiResponse<CouponResponse> get(@PathVariable String ticketId,
        @PathVariable Long couponId,
        @RequestApiKey ApiKeyContext authContext) {
        CouponResponse response = couponCreateFacadeService.getCouponById(authContext.getWorkspaceId(), ticketId, couponId);
        return ApiResponse.success(response);
    }

    @GetMapping("/v1/ticket/{ticketId}/coupon/existence/bulk")
    @Operation(summary = "쿠폰 존재 여부 확인(다건)")
    public ApiResponse<List<CouponExistenceResponse>> checkExistenceBulk(@PathVariable String ticketId,
        @RequestApiKey ApiKeyContext authContext,
        @Valid @RequestBody CouponExistenceBulkRequest request) {
        List<CouponExistenceResponse> responses = couponCreateFacadeService.checkCouponsExistence(
            authContext.getWorkspaceId(), ticketId, request.getCouponIds());
        return ApiResponse.success(responses);
    }
}
