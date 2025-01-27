package com.threedollar.domain.post.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.threedollar.common.QuerydslUtil;
import com.threedollar.domain.post.Post;
import com.threedollar.domain.post.PostGroup;
import com.threedollar.domain.post.PostStatus;

import java.time.LocalDateTime;

import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;

import static com.threedollar.domain.post.QPost.post;
import static com.threedollar.domain.post.postsection.QPostSection.postSection;

@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Post findByIdAndWorkspaceIdAndGroupAndTargetId(String workspaceId, PostGroup group, String targetId, Long postId) {
        return jpaQueryFactory.selectFrom(post)
            .leftJoin(post.postSection, postSection).fetchJoin()
            .where(
                post.workspaceId.eq(workspaceId),
                post.id.eq(postId),
                post.postGroup.eq(group),
                post.targetId.eq(targetId),
                post.status.eq(PostStatus.ACTIVE)
            )
            .fetchOne();
    }

    @Override
    public Post findByIdAndWorkspaceIdAndAccountIdAndGroupAndTargetId(Long postId, String accountId, String workspaceId, PostGroup postGroup, String targetId) {
        return jpaQueryFactory.selectFrom(post)
            .leftJoin(post.postSection, postSection).fetchJoin()
            .where(
                QuerydslUtil.dynamicQuery(accountId != null, () -> post.accountId.eq(accountId)),
                post.workspaceId.eq(workspaceId),
                post.id.eq(postId),
                post.postGroup.eq(postGroup),
                post.targetId.eq(targetId),
                post.status.eq(PostStatus.ACTIVE)
            )
            .fetchOne();
    }

    @Override
    public List<Post> findByPostGroupAndWorkspaceIdAndTargetIdAndCursorAndSizeDesc(PostGroup postGroup, String workspaceId, String targetId, Long cursor, int size) {
        List<Long> postIds = jpaQueryFactory.select(post.id)
            .from(post)
            .where(
                QuerydslUtil.dynamicQuery(cursor != null, () -> post.id.lt(cursor)),
                post.workspaceId.eq(workspaceId),
                post.postGroup.eq(postGroup),
                post.targetId.eq(targetId),
                post.status.eq(PostStatus.ACTIVE)
            )
            .orderBy(post.id.desc())
            .limit(size)
            .fetch();

        return jpaQueryFactory.selectFrom(post)
            .where(post.id.in(postIds))
            .leftJoin(post.postSection, postSection).fetchJoin()
            .orderBy(post.id.desc())
            .fetch();
    }

    @Override
    public List<Post> findByPostGroupAndWorkspaceIdAndTargetIdAndCursorAndSizeAsc(
        PostGroup postGroup,
        String workspaceId, String targetId, Long cursor, int size) {
        List<Long> postIds = jpaQueryFactory.select(post.id)
            .from(post)
            .where(
                post.id.gt(cursor),
                post.workspaceId.eq(workspaceId),
                post.postGroup.eq(postGroup),
                post.targetId.eq(targetId),
                post.status.eq(PostStatus.ACTIVE)
            )
            .orderBy(post.id.asc())
            .limit(size)
            .fetch();

        return jpaQueryFactory.selectFrom(post)
            .where(post.id.in(postIds))
            .leftJoin(post.postSection, postSection).fetchJoin()
            .orderBy(post.id.asc())
            .fetch();
    }


    @Override
    public long postCountByWorkspaceIdAndPostGroupAndTargetIdAndStartTimeAndEndTime(String workspaceId, PostGroup postGroup, String targetId,
        LocalDateTime startTime, LocalDateTime endTime) {
        Long count = jpaQueryFactory.select(post.count())
            .from(post)
            .where(
                post.workspaceId.eq(workspaceId),
                post.status.eq(PostStatus.ACTIVE),
                QuerydslUtil.dynamicQuery(targetId != null, () -> post.targetId.eq(targetId)),
                QuerydslUtil.dynamicQuery(postGroup != null, () -> post.postGroup.eq(postGroup)),
                QuerydslUtil.dynamicQuery(startTime != null, () -> post.createdAt.goe(startTime)),
                QuerydslUtil.dynamicQuery(endTime != null, () -> post.createdAt.loe(endTime))
            ).fetchOne();
        return ObjectUtils.defaultIfNull(count, 0L);
    }


    @Override
    public boolean existPostByPostGroupAndPostIdAndTargetId(PostGroup postGroup, Long postId, String targetId) {

        Integer fetchOne = jpaQueryFactory.selectOne()
            .from(post)
            .where(
                post.postGroup.eq(postGroup),
                post.id.eq(postId),
                post.targetId.eq(targetId),
                post.status.eq(PostStatus.ACTIVE)
            ).fetchFirst();
        return fetchOne != null;
    }

    @Override
    public List<Post> findByPostGroupAndTargetIdAndWorkspaceIdAndIdIn(PostGroup postGroup,
        String targetId, String workspaceId, Set<Long> ids) {

        return jpaQueryFactory.selectFrom(post)
            .where(
                post.workspaceId.eq(workspaceId),
                post.postGroup.eq(postGroup),
                post.targetId.eq(targetId),
                post.id.in(ids)
            )
            .leftJoin(post.postSection, postSection)
            .fetchJoin()
            .fetch();

    }

}
