package com.threedollar.domain.post.repository;

import com.threedollar.domain.post.Post;
import com.threedollar.domain.post.PostGroup;

import java.util.List;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    List<Post> findByPostGroupAndTargetIdAndWorkspaceIdAndIdIn(PostGroup postGroup, String targetId,
        String workspaceId, Set<Long> id);

}
