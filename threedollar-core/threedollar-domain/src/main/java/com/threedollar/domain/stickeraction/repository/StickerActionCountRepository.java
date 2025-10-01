package com.threedollar.domain.stickeraction.repository;

import com.threedollar.domain.sticker.StickerGroup;
import com.threedollar.domain.stickeraction.StickerActionCountKey;
import com.threedollar.infra.redis.StringRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Repository
public class StickerActionCountRepository {

    private final StringRedisRepository<StickerActionCountKey, Long> stickerRedisRepository;

    public void incrBulkByCount(StickerGroup stickerGroup, String workspaceId, String targetId, Set<Long> stickerIds) {
        List<StickerActionCountKey> stickerCountKeys = stickerIds.stream()
            .map(id -> StickerActionCountKey.builder()
                .stickerId(id)
                .workspaceId(workspaceId)
                .targetId(targetId)
                .stickerGroup(stickerGroup)
                .build())
            .toList();
        stickerRedisRepository.incrBulk(stickerCountKeys);

    }

    public void decrBulkByCount(StickerGroup stickerGroup, String workspaceId, String targetId, Set<Long> stickerIds) {
        List<StickerActionCountKey> stickerCountKeys = stickerIds.stream()
            .map(id -> StickerActionCountKey.builder()
                .stickerId(id)
                .workspaceId(workspaceId)
                .targetId(targetId)
                .stickerGroup(stickerGroup)
                .build())
            .toList();
        stickerRedisRepository.decrBulk(stickerCountKeys);
    }


    public Long getStickerCount(StickerActionCountKey stickerActionCountKey) {
        return stickerRedisRepository.get(stickerActionCountKey);
    }

    public void deleteByKey(StickerActionCountKey key) {
        stickerRedisRepository.del(key);
    }

    public void deleteBulkByKeys(List<StickerActionCountKey> keys) {
        stickerRedisRepository.delBulk(keys);
    }

}
