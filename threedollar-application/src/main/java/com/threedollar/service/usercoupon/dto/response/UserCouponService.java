package com.threedollar.service.usercoupon.dto.response;

import com.threedollar.domain.coupon.CouponGroup;
import com.threedollar.domain.coupon.CouponUsageStatus;
import com.threedollar.domain.coupon.repository.UserCouponRepository;

import com.threedollar.domain.coupon.usercoupon.UserCoupon;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCouponService {

    private final UserCouponRepository userCouponRepository;

    @Transactional(readOnly = true)
    public List<UserCouponResponse> getUserCouponList(String workspaceId, CouponGroup couponGroup,
        String accountId, CouponUsageStatus couponUsageStatus) {
        List<UserCoupon> coupons = userCouponRepository.findByWorkspaceIdAndCouponGroupAndAccountIdAndUsageStatus
            (workspaceId, couponGroup, accountId, couponUsageStatus);

        return coupons.stream()
            .map(UserCouponResponse::of)
            .toList();

    }


}
