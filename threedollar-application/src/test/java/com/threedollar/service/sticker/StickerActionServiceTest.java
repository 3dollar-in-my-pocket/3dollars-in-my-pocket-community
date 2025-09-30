package com.threedollar.service.sticker;

import com.threedollar.IntegrationTest;
import com.threedollar.common.exception.NotFoundException;
import com.threedollar.domain.sticker.Sticker;
import com.threedollar.domain.sticker.StickerGroup;
import com.threedollar.domain.sticker.repository.StickerRepository;
import com.threedollar.domain.stickeraction.StickerAction;
import com.threedollar.domain.stickeraction.StickerActionCountKey;
import com.threedollar.domain.stickeraction.repository.StickerActionCountRepository;
import com.threedollar.domain.stickeraction.repository.StickerActionRepository;
import com.threedollar.service.sticker.dto.request.AddStickerActionRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StickerActionServiceTest extends IntegrationTest {

    @Autowired
    private StickerActionRepository stickerActionRepository;

    @Autowired
    private StickerActionService stickerActionService;

    @Autowired
    private StickerRepository stickerRepository;

    @Autowired
    private StickerActionCountRepository stickerActionCountRepository;

    @AfterEach
    void cleanUp() {
        stickerActionRepository.deleteAll();
        stickerRepository.deleteAll();
    }

    @Test
    void 스티커를_추가할_때_해당하는_스티커액션이_없을_때() {
        // given
        Sticker sticker = createSticker();
        AddStickerActionRequest request = getRequest(sticker);
        String workspaceId = sticker.getWorkspaceId(); // 스티커와 동일한 workspaceId 사용
        Set<Long> stickerIds = Set.of(sticker.getId());

        // when
        stickerActionService.upsertSticker(request, sticker.getStickerGroup(), stickerIds, workspaceId);

        // then
        StickerAction stickerAction = StickerAction.newInstance(sticker.getStickerGroup(), workspaceId, stickerIds, request.getAccountId(), request.getTargetId());
        Long stickerCount = getStickerCount(sticker, workspaceId, request.getTargetId());
        assertStickerAction(stickerAction, sticker.getStickerGroup(), stickerAction.getTargetId(), stickerAction.getAccountId(), stickerAction.getStickerIds());
        assertThat(stickerCount).isEqualTo(1L);

    }

    @Test
    void 스티커를_추가할_때_해당하는_스티커액션이_있을_때() {
        // given
        Sticker sticker = createSticker();
        AddStickerActionRequest request = getRequest(sticker);
        String workspaceId = "three-dollar-dev";
        StickerAction stickerAction = StickerAction.newInstance(sticker.getStickerGroup(), workspaceId, Set.of(sticker.getId()), request.getAccountId(), request.getTargetId());
        stickerActionRepository.save(stickerAction);
        stickerActionCountRepository.incrBulkByCount(sticker.getStickerGroup(), workspaceId, request.getTargetId(), Set.of(sticker.getId()));

        // when
        stickerActionService.upsertSticker(request, sticker.getStickerGroup(), Set.of(sticker.getId()), workspaceId);

        // then
        Long stickerCount = getStickerCount(sticker, workspaceId, request.getTargetId());
        assertThat(stickerCount).isEqualTo(1L);
    }


    @Test
    void 스티커를_제거한다() {
        // given
        Sticker sticker = createSticker();
        String accountId = "USER999L";
        String targetId = "1";
        String workspaceId = "three-dollar-dev-2";
        StickerAction stickerAction = stickerActionRepository.save(StickerAction.newInstance(sticker.getStickerGroup(), workspaceId, Set.of(sticker.getId()), accountId, targetId));
        stickerActionCountRepository.incrBulkByCount(sticker.getStickerGroup(), workspaceId, targetId, Set.of(sticker.getId()));

        // when
        stickerActionService.deleteStickers(sticker.getStickerGroup(), workspaceId, stickerAction.getTargetId(), stickerAction.getAccountId());

        // then
        List<StickerAction> stickerActionList = stickerActionRepository.findAll();

        Long stickerCount = getStickerCount(sticker, workspaceId, targetId);
        assertThat(stickerCount).isEqualTo(0);
        assertThat(stickerActionList).isEmpty();
    }

    @Test
    void 해당하는_스티커가_없을_때_제거하는_경우() {
        // given
        Sticker sticker = createSticker();
        String targetId = "poll999";
        String accountId = "user111";

        // when & then
        assertThatThrownBy(() -> stickerActionService.deleteStickers(sticker.getStickerGroup(), sticker.getWorkspaceId(), targetId, accountId))
            .isInstanceOf(NotFoundException.class);

    }

    private void assertStickerAction(StickerAction stickerAction, StickerGroup stickerGroup, String targetId, String accountId, Set<Long> stickerIds) {
        assertThat(stickerAction.getStickerGroup()).isEqualTo(stickerGroup);
        assertThat(stickerAction.getTargetId()).isEqualTo(targetId);
        assertThat(stickerAction.getAccountId()).isEqualTo(accountId);
        assertThat(stickerAction.getStickerIds()).isEqualTo(stickerIds);
    }


    private AddStickerActionRequest getRequest(Sticker sticker) {
        String targetId = "POLL900";
        String accountId = "USER_ACCOUNT999L";
        return AddStickerActionRequest.builder()
            .targetId(targetId)
            .stickerNames(Set.of(sticker.getName()))
            .accountId(accountId)
            .build();
    }

    private Sticker createSticker() {
        String imageUrl = "imageUrl";
        String workspaceId = "three-dollar-test-22";
        StickerGroup stickerGroup = StickerGroup.POLL_COMMENT;
        String stickerName = "LIKE";
        return stickerRepository.save(Sticker.newInstance(imageUrl, workspaceId, stickerName, stickerGroup));
    }

    private Long getStickerCount(Sticker sticker, String workspaceId, String targetId) {
        return stickerActionCountRepository.getStickerCount(StickerActionCountKey.of(sticker.getStickerGroup(), workspaceId, targetId, sticker.getId()));
    }

}
