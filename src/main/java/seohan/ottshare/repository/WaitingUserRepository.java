package seohan.ottshare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seohan.ottshare.entity.WaitingUser;
import seohan.ottshare.repository.custom.WaitingUserRepositoryCustom;

@Repository
public interface WaitingUserRepository extends JpaRepository<WaitingUser, Long> , WaitingUserRepositoryCustom {
}
