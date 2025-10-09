package com.threedollar.domain.coupon.repository;

import com.threedollar.domain.coupon.Coupon;
import com.threedollar.domain.coupon.CouponStatus;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long>, CouponRepositoryCustom {

    Coupon findByIdAndWorkspaceIdAndTicketIdAndStatusIn(Long id, String workspaceId, String ticketId, Collection<CouponStatus> status);

    List<Coupon> findByWorkspaceIdAndTicketIdAndProviderIdAndStatusIn(
        String workspaceId, String ticketId, String providerId, Collection<CouponStatus> status);
}
