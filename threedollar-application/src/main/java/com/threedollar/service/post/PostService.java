package com.threedollar.service.post;

import com.threedollar.common.exception.InvalidRequestException;
import com.threedollar.common.exception.NotFoundException;
import com.threedollar.domain.post.Post;
import com.threedollar.domain.post.PostGroup;
import com.threedollar.domain.post.postsection.PostSection;
import com.threedollar.domain.post.repository.PostRepository;
import com.threedollar.service.post.request.CursorDirection;
import com.threedollar.service.post.request.PostAddRequest;
import com.threedollar.service.post.request.PostUpdateRequest;
import com.threedollar.service.post.response.PostAndCursorResponse;
import com.threedollar.service.post.response.PostResponse;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;


    @Transactional
    public Long addPost(
        PostGroup postGroup,
        String targetId,
        PostAddRequest request,
        @NotBlank String workspaceId,
        String accountId) {

        Post post = postRepository.save(request.toEntity(postGroup, workspaceId, accountId, targetId));
        return post.getId();

    }

    @Transactional
    public void deletePost(String workspaceId,
                           String accountId,
                           Long postId,
                           PostGroup postGroup,
                           String targetId) {

        Post post = getPostByIdAndAccountId(workspaceId, postGroup, targetId, postId, accountId);
        post.delete();

    }

    @Transactional(readOnly = true)
    public PostAndCursorResponse getPostsAndCursor(PostGroup postGroup,
                                                   String workspaceId,
                                                   String targetId,
                                                   String accountId,
                                                   Long cursor,
                                                   CursorDirection cursorDirection,
                                                   int size) {

        if (cursorDirection.equals(CursorDirection.UP)) {
            if (cursor == null) {
                throw new InvalidRequestException("cursor 는 null 일 수 없습니다.");
            }
            List<Post> scrollUpPosts = postRepository.findByPostGroupAndWorkspaceIdAndTargetIdAndCursorAndSizeAsc(postGroup, workspaceId, targetId, cursor, size + 1);
            return getPostAndCursorResponse(scrollUpPosts, size, accountId);
        }
        List<Post> scrollDownPosts = postRepository.findByPostGroupAndWorkspaceIdAndTargetIdAndCursorAndSizeDesc(postGroup, workspaceId, targetId, cursor, size + 1);
        return getPostAndCursorResponse(scrollDownPosts, size, accountId);

    }

    private PostAndCursorResponse getPostAndCursorResponse(List<Post> scrolledPosts, int size, String accountId) {
        if (scrolledPosts.isEmpty() || scrolledPosts.size() <= size){
            return PostAndCursorResponse.noMore(scrolledPosts, accountId);
        }
        return PostAndCursorResponse.hasMore(scrolledPosts, accountId);
    }

    @Transactional(readOnly = true)
    public PostResponse getPostById(String workspaceId,
                                    String accountId,
                                    Long postId,
                                    PostGroup postGroup,
                                    String targetId) {
        Post post = postRepository.findByIdAndWorkspaceIdAndGroupAndTargetId(workspaceId, postGroup, targetId, postId);
        if (post == null) {
            throw new NotFoundException(String.format("(%s)에 해당하는 post 는 존재하지 않습니다.", postId));
        }
        return PostResponse.of(post, accountId);
    }

    @Transactional(readOnly = true)
    public Long getPostCountByTargetId(String workspaceId,
                                       PostGroup postGroup,
                                       String targetId,
        LocalDateTime startTime, LocalDateTime endTime) {
        return Math.max(0, postRepository.postCountByWorkspaceIdAndPostGroupAndTargetIdAndStartTimeAndEndTime(workspaceId, postGroup, targetId,
            startTime, endTime));
    }

    @Transactional
    public PostResponse update(String workspaceId,
                               String accountId,
                               Long postId,
                               PostGroup postGroup,
                               String targetId,
                               PostUpdateRequest request) {

        Post post = getPostByIdAndAccountId(workspaceId, postGroup, targetId, postId, accountId);
        post.update(request.getTitle(), request.getContent(), getPostSections(request, post));
        return PostResponse.of(post, accountId);
    }

    @Transactional(readOnly = true)
    public boolean existsPost(PostGroup postGroup,
        Long postId,
        String targetId) {

        return postRepository.existPostByPostGroupAndPostIdAndTargetId(postGroup, postId, targetId);

    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByPostIds(String workspaceId, PostGroup postGroup,
        String targetId, Set<Long> postIds, String accountId) {

        List<Post> posts = postRepository.findByPostGroupAndTargetIdAndWorkspaceIdAndIdIn(postGroup, targetId,
            workspaceId, postIds);

        return posts.stream()
            .map(post -> PostResponse.of(post, accountId))
            .collect(Collectors.toList());
    }

    private List<PostSection> getPostSections(PostUpdateRequest request, Post post) {
        if (request.getSections() == null) {
            return null;
        }
        return request.getSections().stream()
            .map(r -> r.toEntity(post))
            .collect(Collectors.toList());
    }


    private Post getPostByIdAndAccountId(String workspaceId,
                                         PostGroup postGroup,
                                         String targetId,
                                         Long postId,
                                         String accountId) {
        Post post = postRepository.findByIdAndWorkspaceIdAndAccountIdAndGroupAndTargetId(postId, accountId, workspaceId, postGroup, targetId);
        if (post == null) {
            throw new NotFoundException(String.format("(%s)에 해당하는 postId 는 계정(%s)의 소유가 아니거나 존재하지 않습니다", postId, accountId));
        }
        return post;

    }

}
