package com.threedollar.controller.coupon;

import com.threedollar.common.dto.response.ApiResponse;
import com.threedollar.config.interceptor.ApiKeyContext;
import com.threedollar.config.resolver.RequestApiKey;
import com.threedollar.service.CouponCreateFacadeService;
import com.threedollar.service.coupon.CouponIssueFacadeService;
import com.threedollar.service.coupon.dto.response.CouponExistenceResponse;
import com.threedollar.service.coupon.dto.response.CouponResponse;
import com.threedollar.service.coupon.dto.request.CouponCreateRequest;
import com.threedollar.service.coupon.dto.request.CouponExistenceBulkRequest;
import com.threedollar.service.coupon.dto.request.CouponUseRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "Coupon API")
public class CouponController {

    private final CouponCreateFacadeService couponCreateFacadeService;
    private final CouponIssueFacadeService couponIssueFacadeService;

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

    @PostMapping("/v1/ticket/{ticketId}/coupon/{couponId}/use")
    @Operation(summary = "쿠폰 사용 처리")
    public ApiResponse<String> use(@PathVariable String ticketId,
        @PathVariable Long couponId,
        @RequestApiKey ApiKeyContext authContext,
        @Valid @RequestBody CouponUseRequest request) {
        couponIssueFacadeService.use(authContext.getWorkspaceId(), ticketId, couponId, request.getOwnerId());
        return ApiResponse.OK;
    }



}
