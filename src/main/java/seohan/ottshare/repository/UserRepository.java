package seohan.ottshare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seohan.ottshare.entity.User;
import seohan.ottshare.repository.custom.UserRepositoryCustom;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    boolean existsByUsername(String username);
}
