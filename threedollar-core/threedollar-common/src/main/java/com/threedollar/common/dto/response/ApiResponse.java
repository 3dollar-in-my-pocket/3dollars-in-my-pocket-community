package com.threedollar.common.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.threedollar.common.exception.ErrorCode;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

@EqualsAndHashCode
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    public static final ApiResponse<String> OK = success(null);

    private boolean ok;

    private String error;

    private T data;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> reasons;

    private ApiResponse(boolean ok, String error, T data, List<String> reason) {
        this.ok = ok;
        this.error = error;
        this.reasons = reason;
        this.data = data;
    }

    @NotNull
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, null, data, Collections.emptyList());
    }

    @NotNull
    public static <T> ApiResponse<T> error(ErrorCode errorCode) {
        return new ApiResponse<>(false, errorCode.getCode(), null, Collections.emptyList());
    }

    @NotNull
    public static <T> ApiResponse<T> error(ErrorCode errorCode, List<String> reasons) {
        return new ApiResponse<>(false, errorCode.getCode(), null, reasons);
    }

}
