package seohan.ottshare.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import seohan.ottshare.entity.WaitingUser;
import seohan.ottshare.enums.OttType;

import java.util.List;
import java.util.Optional;

import static seohan.ottshare.entity.QUser.user;
import static seohan.ottshare.entity.QWaitingUser.waitingUser;

@Repository
@RequiredArgsConstructor
public class WaitingUserRepositoryCustomImpl implements WaitingUserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<WaitingUser> findLeaderByOtt(OttType ottType) {
        return Optional.ofNullable(queryFactory
                .selectFrom(waitingUser)
                .join(waitingUser.user, user).fetchJoin()
                .where(waitingUser.ott.eq(ottType)
                        .and(waitingUser.isLeader.isTrue()))
                .orderBy(waitingUser.createdDate.asc())
                .fetchOne());
    }

    @Override
    public Optional<WaitingUser> findByUserId(Long userId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(waitingUser)
                .where(waitingUser.user.id.eq(userId))
                .fetchOne()
        );
    }


    @Override
    public List<WaitingUser> findNoneLeaderByOtt(OttType ottType, int limit) {
        return queryFactory
                .selectFrom(waitingUser)
                .join(waitingUser.user, user).fetchJoin()
                .where(waitingUser.ott.eq(ottType)
                        .and(waitingUser.isLeader.isFalse()))
                .orderBy(waitingUser.createdDate.asc())
                .limit(limit)
                .fetch();
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        queryFactory.delete(waitingUser)
                .where(waitingUser.id.in(ids))
                .execute();
    }
}
