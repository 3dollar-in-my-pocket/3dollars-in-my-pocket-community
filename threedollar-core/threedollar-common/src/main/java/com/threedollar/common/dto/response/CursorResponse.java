package com.threedollar.common.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CursorResponse {

    private boolean hasNext;

    private boolean hasMore;

    private Long nextCursor;

    @Builder
    public CursorResponse(boolean hasNext, boolean hasMore, Long nextCursor) {
        this.hasNext = hasNext;
        this.hasMore = hasMore;
        this.nextCursor = nextCursor;
    }

    public static CursorResponse hasNext(Long nextCursor) {
        return CursorResponse.builder()
            .hasNext(true)
            .hasMore(true)
            .nextCursor(nextCursor)
            .build();
    }

    public static CursorResponse noMore() {
        return CursorResponse.builder()
            .hasNext(false)
            .hasMore(false)
            .nextCursor(null)
            .build();
    }

}
