package seohan.ottshare.repository.custom;

import seohan.ottshare.entity.SharingUser;

import java.util.List;
import java.util.Optional;

public interface SharingUserRepositoryCustom {

    List<SharingUser> findByIds(List<Long> sharingUserIds);

    Optional<SharingUser> findByRoomIdAndUserId(Long roomId, Long userId);

    Optional<SharingUser> findByUserId(Long userId);

    void deleteByOttShareId(Long roomId);
}
