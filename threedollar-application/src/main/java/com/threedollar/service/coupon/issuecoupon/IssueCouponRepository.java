package com.threedollar.service.coupon.issuecoupon;

import com.threedollar.domain.coupon.IssueCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IssueCouponRepository extends JpaRepository<IssueCoupon, Long> {

    // TODO: querydsl 로 쿼리 개선
    boolean existsByWorkspaceIdAndTicketIdAndIdAndOwnerId(String workspaceId, String ticketId, Long couponId, String ownerId);

    Optional<IssueCoupon> findByWorkspaceIdAndTicketIdAndCouponIdAndOwnerId(String workspaceId, String ticketId, Long couponId, String ownerId);

}
