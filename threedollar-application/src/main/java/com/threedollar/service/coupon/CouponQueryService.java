package com.threedollar.service.coupon;


import com.threedollar.common.exception.NotFoundException;
import com.threedollar.domain.coupon.Coupon;
import com.threedollar.domain.coupon.repository.CouponRepository;
import com.threedollar.domain.coupon.CouponStatus;

import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@RequiredArgsConstructor
@Service
public class CouponQueryService {

    private final CouponRepository couponRepository;

    @Transactional(readOnly = true)
    public Coupon couponById(String workspaceId, String ticketId, Long couponId, Collection<CouponStatus> status) {
        Coupon coupon = couponRepository.findByIdAndWorkspaceIdAndTicketIdAndStatusIn(couponId, workspaceId, ticketId,
            status);
        if (coupon == null) {
            throw new NotFoundException(String.format
                ("쿠폰 id: (%s) workspaceId: (%s) ticketId: (%s) 를 찾을 수 없습니다.", couponId,
                    workspaceId, ticketId));
        }
        return coupon;
    }

    @Transactional(readOnly = true)
    public Set<Long> findExistingCouponIds(String workspaceId, String ticketId, Set<Long> couponIds,
        Collection<CouponStatus> status) {
        return couponRepository.findExistingIds(workspaceId, ticketId, couponIds, status);
    }

    @Transactional(readOnly = true)
    public List<Coupon> findCouponsByProvider(String workspaceId, String ticketId, String providerId,
        Collection<CouponStatus> status) {
        return couponRepository.findByWorkspaceIdAndTicketIdAndProviderIdAndStatusIn(
            workspaceId, ticketId, providerId, status);
    }

    @Transactional(readOnly = true)
    public List<Coupon> findCouponsInfoWithLimit(String workspaceId, String ticketId, String providerId,
        Collection<CouponStatus> status, int size) {
        return couponRepository.findByCouponInfoWithLimit(workspaceId, ticketId,
            providerId, status, size);
    }

}

