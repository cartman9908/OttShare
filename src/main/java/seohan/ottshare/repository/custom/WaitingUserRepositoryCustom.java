package seohan.ottshare.repository.custom;

import seohan.ottshare.entity.WaitingUser;
import seohan.ottshare.enums.OttType;

import java.util.List;
import java.util.Optional;

public interface WaitingUserRepositoryCustom {
    Optional<WaitingUser> findLeaderByOtt(OttType ottType);

    Optional<WaitingUser> findByUserId(Long userId);

    List<WaitingUser> findNoneLeaderByOtt(OttType ottType, int limit);

    void deleteByIds(List<Long> ids);
}
