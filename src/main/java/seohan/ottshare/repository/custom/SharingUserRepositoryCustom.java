package seohan.ottshare.repository.custom;

import seohan.ottshare.entity.SharingUser;

import java.util.List;

public interface SharingUserRepositoryCustom {

    List<SharingUser> findByIds(List<Long> sharingUserIds);
}
