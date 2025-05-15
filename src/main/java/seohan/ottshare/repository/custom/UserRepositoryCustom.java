package seohan.ottshare.repository.custom;

import seohan.ottshare.entity.User;

import java.util.Optional;

public interface UserRepositoryCustom {

    Optional<User> findByUsername(String username);

    Optional<User> findByIdAndUsername(Long userId, String username);
}
