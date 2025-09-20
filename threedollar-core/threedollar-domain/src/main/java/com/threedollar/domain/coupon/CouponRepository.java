package com.threedollar.domain.coupon;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long>, CouponCustomRepository {

    Coupon findByIdAndWorkspaceIdAndTicketIdAndStatusIn(Long id, String workspaceId, String ticketId, Collection<CouponStatus> status);

    List<Coupon> findByWorkspaceIdAndTicketIdAndProviderIdAndStatusIn(
        String workspaceId, String ticketId, String providerId, Collection<CouponStatus> status);

}
