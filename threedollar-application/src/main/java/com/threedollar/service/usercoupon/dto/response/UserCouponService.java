package com.threedollar.service.usercoupon.dto.response;

import com.threedollar.common.dto.response.CursorResponse;
import com.threedollar.common.exception.ConflictException;
import com.threedollar.common.exception.InvalidException;
import com.threedollar.common.exception.NotFoundException;
import com.threedollar.domain.coupon.Coupon;
import com.threedollar.domain.coupon.CouponGroup;
import com.threedollar.domain.coupon.CouponUsageStatus;
import com.threedollar.domain.coupon.repository.CouponRepository;
import com.threedollar.domain.coupon.repository.UserCouponRepository;

import com.threedollar.domain.coupon.repository.redis.UsedCouponCountRepository;
import com.threedollar.domain.coupon.repository.redis.couponissuecount.CouponIssueCountKey;
import com.threedollar.domain.coupon.repository.redis.couponissuecount.CouponIssueCountRepository;
import com.threedollar.domain.coupon.usercoupon.UserCoupon;

import com.threedollar.service.coupon.CouponServiceHelper;

import java.time.LocalDateTime;
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
    private final UsedCouponCountRepository usedCouponCountRepository;
    private final CouponIssueCountRepository couponIssueCountRepository;

    private final CouponServiceHelper couponServiceHelper;
    private final CouponRepository couponRepository;

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

    @Transactional
    public void issueCoupon(String workspaceId, CouponGroup couponGroup, Long couponId, String accountId) {
        boolean existsCoupon = userCouponRepository.existsByWorkspaceIdAndCouponGroupAndCouponIdAndAccountId(
            workspaceId, couponGroup, couponId, accountId
        );
        if (existsCoupon) {
            throw new InvalidException(String.format("유저(%s) 가 발급 받은 쿠폰 (%s) 이 이미 존재합니다.",
                accountId, couponId));
        }

        Coupon coupon = couponRepository.findWithLockById(couponId);
        if (coupon == null) {
            throw new ConflictException(String.format("쿠폰 (%s) 에 해당하는 couponId 가 존재하지 않습니다.",
                couponId.toString()));
        }

        // 쿠폰 발급 가능 여부 확인
        coupon.validateCoupon();
        if (couponIssueCountRepository.getValueByKey(
            CouponIssueCountKey.of(couponId, coupon.getWorkspaceId(), coupon.getProviderId())) >= coupon.getMaxCount()) {
            throw new InvalidException(String.format("쿠폰 (%s) 의 발급 가능 개수(%s) 를 초과했습니다.",
                couponId, coupon.getMaxCount()));
        }

        // 고객이 쿠폰 발급
        UserCoupon userCoupon = UserCoupon.newInstance(couponId, workspaceId,
            couponGroup, accountId, coupon.getCouponTime().getAvailableStartTime(),
            coupon.getCouponTime().getAvailableEndTime());
        userCouponRepository.save(userCoupon);

        // 발급한 쿠폰 개수 증가
        couponIssueCountRepository.incrByCount(coupon.getId(), coupon.getWorkspaceId(), coupon.getProviderId());

    }

    @Transactional
    public void useCoupon(String workspaceId, CouponGroup couponGroup, String accountId, Long couponId) {

        UserCoupon userCoupon = userCouponRepository.findByWorkspaceIdAndCouponGroupAndCouponIdAndAccountId(
            workspaceId, couponGroup, couponId, accountId
        );

        if (userCoupon == null) {
            throw new NotFoundException(String.format("유저(%s) 가 발급 받은 쿠폰 (%s) 이 존재하지 않습니다.", accountId, couponId));
        }

        Coupon coupon = couponRepository.findWithLockById(couponId);
        if (coupon == null) {
            throw new NotFoundException(String.format("쿠폰 (%s) 에 해당하는 couponId 가 존재하지 않습니다.",  couponId));
        }

        userCoupon.use(LocalDateTime.now());

        // redis 기반 사용한 쿠폰 수 증가
        usedCouponCountRepository.incrByCount(coupon.getId(), workspaceId, coupon.getProviderId());

    }


}
