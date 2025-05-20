package seohan.ottshare.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import seohan.ottshare.entity.SharingUser;

import java.util.List;

import static seohan.ottshare.entity.QSharingUser.sharingUser;

@Repository
@RequiredArgsConstructor
public class SharingUserRepositoryCustomImpl implements SharingUserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<SharingUser> findByIds(List<Long> sharingUserIds) {
        return queryFactory
                .selectFrom(sharingUser)
                .join(sharingUser.user).fetchJoin()
                .where(sharingUser.id.in(sharingUserIds))
                .fetch();
    }
}