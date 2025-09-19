package com.threedollar.domain.coupon;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    Coupon findByIdAndWorkspaceIdAndTicketIdAndStatusIn(Long id, String workspaceId, String ticketId, Collection<CouponStatus> status);
}
