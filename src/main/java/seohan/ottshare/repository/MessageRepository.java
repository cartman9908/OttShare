package seohan.ottshare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seohan.ottshare.entity.Message;
import seohan.ottshare.repository.custom.MessageRepositoryCustom;

public interface MessageRepository extends JpaRepository<Message, Integer>, MessageRepositoryCustom {

}
