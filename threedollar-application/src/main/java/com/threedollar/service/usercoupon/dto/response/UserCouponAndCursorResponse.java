package com.threedollar.service.usercoupon.dto.response;

import com.threedollar.common.dto.response.CursorResponse;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserCouponAndCursorResponse {

    private CursorResponse cursorResponse;

    private List<UserCouponResponse> userCouponResponses;

    @Builder
    public UserCouponAndCursorResponse(CursorResponse cursorResponse,
        List<UserCouponResponse> userCouponResponses) {
        this.cursorResponse = cursorResponse;
        this.userCouponResponses = userCouponResponses;
    }

}
