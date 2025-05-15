package seohan.ottshare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seohan.ottshare.entity.OttShareRoom;

@Repository
public interface OttShareRoomRepository extends JpaRepository<OttShareRoom, Long> {
}
