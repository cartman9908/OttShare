package seohan.ottshare.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import seohan.ottshare.entity.QMessage;


@Repository
@RequiredArgsConstructor
public class MessageRepositoryCustomImpl implements MessageRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public void deleteByOttShareId(Long roomId) {
        jpaQueryFactory.delete(QMessage.message1)
                .where(QMessage.message1.ottShareRoom.id.eq(roomId))
                .execute();
    }
}
