package com.threedollar.service.usercoupon.dto.response;

import com.threedollar.common.dto.response.CursorResponse;
import com.threedollar.domain.coupon.Coupon;
import com.threedollar.domain.coupon.CouponGroup;
import com.threedollar.domain.coupon.CouponUsageStatus;
import com.threedollar.domain.coupon.repository.UserCouponRepository;

import com.threedollar.domain.coupon.usercoupon.UserCoupon;

import com.threedollar.service.coupon.CouponServiceHelper;

import java.util.HashMap;
import java.util.List;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCouponService {

    private final UserCouponRepository userCouponRepository;

    private final CouponServiceHelper couponServiceHelper;

    @Transactional(readOnly = true)
    public UserCouponAndCursorResponse getUserCouponList(String workspaceId, CouponGroup couponGroup,
        String accountId, CouponUsageStatus couponUsageStatus, Long cursor, int size) {
        List<UserCoupon> coupons = userCouponRepository.findUserCoupon(workspaceId, couponGroup, accountId, couponUsageStatus, cursor, size + 1);

        Map<Long, UserCoupon> couponMap = new HashMap<>();
        for (UserCoupon coupon : coupons) {
            couponMap.put(coupon.getCouponId(), coupon);
        }

        List<Long> couponIds = coupons.stream()
            .map(UserCoupon::getCouponId)
            .toList();

        // TODO: Caching
        List<Coupon> couponList = couponServiceHelper.getCouponList(couponIds);

        List<UserCouponResponse> userCouponResponses = couponList.stream()
            .map(coupon -> UserCouponResponse.of(couponMap.get(coupon.getId()), coupon))
            .toList();

        if (coupons.isEmpty() || coupons.size() < size + 1) {
            return UserCouponAndCursorResponse.builder()
                .cursorResponse(CursorResponse.noMore())
                .userCouponResponses(userCouponResponses)
                .build();
        }
        return UserCouponAndCursorResponse.builder()
            .cursorResponse(CursorResponse.hasNext(coupons.get(coupons.size() - 1).getId()))
            .userCouponResponses(userCouponResponses)
            .build();
    }


}
