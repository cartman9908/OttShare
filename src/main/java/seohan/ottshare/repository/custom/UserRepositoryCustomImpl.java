package seohan.ottshare.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import seohan.ottshare.entity.User;

import java.util.Optional;

import static seohan.ottshare.entity.QSharingUser.sharingUser;
import static seohan.ottshare.entity.QUser.user;
import static seohan.ottshare.entity.QWaitingUser.waitingUser;

@RequiredArgsConstructor
@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(queryFactory
                .selectFrom(user)
                .leftJoin(user.sharingUser, sharingUser).fetchJoin()
                .leftJoin(user.waitingUser, waitingUser).fetchJoin()
                .where(user.username.eq(username))
                .fetchOne());
    }

    @Override
    public Optional<User> findByIdAndUsername(Long userId, String username) {
        return Optional.ofNullable(queryFactory
                .selectFrom(user)
                .where(user.id.eq(userId),
                        user.username.eq(username))
                .fetchOne());
    }
}
