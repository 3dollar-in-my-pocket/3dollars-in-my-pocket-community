package com.threedollar.common.exception;


import lombok.Getter;

@Getter
public enum ErrorCode {
    E400_INVALID(400, "invalid_request", "잘못된 요청입니다"),
    E400_INVALID_COUPON_ISSUE_PERIOD(400, "invalid_coupon_issue_time", "쿠폰 발급 가능 시간은 현재 시간보다 이전일 수 없습니다"),
    E400_INVALID_COUPON_AVAILABLE_PERIOD(400, "invalid_coupon_available_time", "쿠폰 사용 가능 시간은 현재 시간보다 이전일 수 없습니다"),
    E401_INVALID_API_KEY(401, "invalid_api_key", "등록되지 않은 Api-Key 입니다"),
    E404_NOT_FOUND(404, "not_found", "찾을 수 없습니다"),
    E409_CONFLICT(409, "conflict", "서버의 현재 상태와 요청이 충돌합니다"),
    E422_UNPROCESSABLE(422, "unprocessable",  "진행할 수 없습니다."),
    E500_INTERNAL_SERVER(500, "internal_server", "서버 내부적으로 에러가 발생하였습니다"),
    E503_SERVICE_UNAVAILABLE(503, "service_unavailable", "일시적으로 서비스를 이용할 수 없습니다"),
    ;

    private final int status;

    private final String code;

    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }


}
