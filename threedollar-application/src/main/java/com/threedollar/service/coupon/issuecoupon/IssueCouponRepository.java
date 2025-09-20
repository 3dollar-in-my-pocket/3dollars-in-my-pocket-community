package com.threedollar.service.coupon.issuecoupon;

import com.threedollar.domain.coupon.IssueCoupon;

import com.threedollar.domain.coupon.IssueCouponStatus;

import java.util.List;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueCouponRepository extends JpaRepository<IssueCoupon, Long> {

    // TODO: querydsl 로 쿼리 개선
    boolean existsByWorkspaceIdAndTicketIdAndIdAndOwnerId(String workspaceId, String ticketId, Long couponId, String ownerId);

    IssueCoupon findByWorkspaceIdAndTicketIdAndCouponIdAndOwnerId(String workspaceId, String ticketId, Long couponId, String ownerId);

    List<IssueCoupon> findByWorkspaceIdAndTicketIdAndOwnerIdAndStatusIn(String workspaceId, String ticketId,
        String ownerId, Set<IssueCouponStatus> status);

}
