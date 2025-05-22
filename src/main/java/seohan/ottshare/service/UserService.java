package seohan.ottshare.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seohan.ottshare.dto.userDto.UserRequest;
import seohan.ottshare.dto.userDto.UserResponse;
import seohan.ottshare.dto.userDto.UserUpdateReq;
import seohan.ottshare.entity.User;
import seohan.ottshare.exception.UserNotFoundException;
import seohan.ottshare.repository.UserRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    /**
     * 회원가입
     */
    @Transactional
    public UserResponse createUser(UserRequest request) {

        request.setPassword(encoder.encode(request.getPassword()));
        User user = User.from(request);

        userRepository.save(user);

        return UserResponse.from(user);
    }

    public UserResponse getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return UserResponse.from(user);
    }

    /**
     * user 삭제
     */
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userRepository.delete(user);
    }

    public UserResponse findUserForPasswordReset(Long userId, String username) {
        User user = userRepository.findByIdAndUsername(userId, username)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return UserResponse.from(user);
    }

    /**
     * user 수정
     */
    @Transactional
    public void updateUser(Long userId, UserUpdateReq req) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        req.setPassword(encoder.encode(req.getPassword()));
        user.update(req);
    }

    /**
     * 비밀번호 수정
     */
    @Transactional
    public void updatePassword(Long userId, String password) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.updatePassword(encoder.encode(password));
    }
}
