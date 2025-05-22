package seohan.ottshare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seohan.ottshare.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {

}
