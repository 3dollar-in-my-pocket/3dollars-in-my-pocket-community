package com.threedollar.domain.coupon;

import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DateTimeInterval {

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

}
