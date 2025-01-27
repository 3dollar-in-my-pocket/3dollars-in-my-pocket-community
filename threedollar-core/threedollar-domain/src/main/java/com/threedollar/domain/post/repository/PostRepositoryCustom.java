package com.threedollar.domain.post.repository;

import com.threedollar.domain.post.Post;
import com.threedollar.domain.post.PostGroup;
import jakarta.annotation.Nullable;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


public interface PostRepositoryCustom {

    Post findByIdAndWorkspaceIdAndGroupAndTargetId(
        String workspaceId,
        PostGroup group,
        String targetId,
        Long postId
    );

    Post findByIdAndWorkspaceIdAndAccountIdAndGroupAndTargetId(Long postId,
                                                               @Nullable String accountId,
                                                               String workspaceId,
                                                               PostGroup group,
                                                               String targetId);

    List<Post> findByPostGroupAndWorkspaceIdAndTargetIdAndCursorAndSizeDesc(PostGroup postGroup,
                                                                        String workspaceId,
                                                                        String targetId,
                                                                        @Nullable Long cursor,
                                                                        int size);

    List<Post> findByPostGroupAndWorkspaceIdAndTargetIdAndCursorAndSizeAsc(PostGroup postGroup,
        String workspaceId,
        String targetId,
        @NotNull Long cursor,
        int size);

    long postCountByWorkspaceIdAndPostGroupAndTargetIdAndStartTimeAndEndTime(String workspaceId,
                                                       PostGroup postGroup,
                                                       String targetId,
        LocalDateTime startTime, LocalDateTime endTime);

    boolean existPostByPostGroupAndPostIdAndTargetId(PostGroup postGroup,
        Long postId,
        String targetId);

    List<Post> findByPostGroupAndTargetIdAndWorkspaceIdAndIdIn(PostGroup postGroup, String targetId,
        String workspaceId, Set<Long> ids);
}
