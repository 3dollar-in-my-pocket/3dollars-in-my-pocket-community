package com.threedollar.domain.coupon;

import com.threedollar.common.exception.ErrorCode;
import com.threedollar.common.exception.InvalidException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Getter
public class CouponTime {

    @Column(nullable = false)
    private LocalDateTime issueStartTime;

    @Column(nullable = false)
    private LocalDateTime issueEndTime;

    @Column(nullable = false)
    private LocalDateTime availableStartTime;

    @Column(nullable = false)
    private LocalDateTime availableEndTime;

    public CouponTime(LocalDateTime issueStartTime, LocalDateTime issueEndTime, LocalDateTime availableStartTime,
        LocalDateTime availableEndTime) {
        validateCouponTime(issueStartTime, issueEndTime, ErrorCode.E400_INVALID_COUPON_ISSUE_PERIOD);
        validateCouponTime(availableStartTime, availableEndTime, ErrorCode.E400_INVALID_COUPON_AVAILABLE_PERIOD);
        this.issueStartTime = issueStartTime;
        this.issueEndTime = issueEndTime;
        this.availableStartTime = availableStartTime;
        this.availableEndTime = availableEndTime;
    }

    public static void validateCouponTime(LocalDateTime startTime, LocalDateTime endTime, ErrorCode errorCode) {
        if (startTime.isAfter(endTime)) {
            throw new InvalidException(errorCode);
        }
    }
}
