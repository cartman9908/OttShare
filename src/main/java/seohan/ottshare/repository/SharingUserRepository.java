package seohan.ottshare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seohan.ottshare.entity.SharingUser;

@Repository
public interface SharingUserRepository extends JpaRepository<SharingUser, Long> {
}
