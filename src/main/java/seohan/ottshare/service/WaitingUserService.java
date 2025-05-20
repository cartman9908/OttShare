package seohan.ottshare.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seohan.ottshare.dto.waitingUserDto.IsLeaderAndOttResponse;
import seohan.ottshare.dto.waitingUserDto.WaitingUserReq;
import seohan.ottshare.dto.waitingUserDto.WaitingUserResponse;
import seohan.ottshare.entity.User;
import seohan.ottshare.entity.WaitingUser;
import seohan.ottshare.enums.OttType;
import seohan.ottshare.exception.NotOttLeaderException;
import seohan.ottshare.exception.OttLeaderNotFoundException;
import seohan.ottshare.repository.UserRepository;
import seohan.ottshare.repository.WaitingUserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WaitingUserService {

    private final UserRepository userRepository;
    private final WaitingUserRepository waitingUserRepository;

    /**
     * user 저장
     */
    @Transactional
    public void createWaitingUser(WaitingUserReq waitingUserReq) {
        User user = userRepository.findByUsername(waitingUserReq.getUserInfo().getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다."));

        WaitingUser waitingUser = WaitingUser.from(waitingUserReq, user);
        waitingUserRepository.save(waitingUser);
    }

    /**
     * user 삭제(매칭 취소)
     */
    @Transactional
    public void deleteUser(Long id) {
        WaitingUser waitingUser = waitingUserRepository.findByUserId(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID로 사용자를 찾을 수 없습니다. " + id));

        waitingUserRepository.delete(waitingUser);
    }

    /**
     * user 삭제(대기열에서 삭제)
     */
    @Transactional
    public void deleteUsers(List<WaitingUserResponse> waitingUserResponses) {
        List<Long> ids = waitingUserResponses.stream()
                .map(WaitingUserResponse::getId)
                .toList();

        waitingUserRepository.deleteByIds(ids);
    }

    /**
     * userId로 waitingUserId 반환
     */
    public Optional<Long> getWaitingUserIdByUserId(Long userId) {
        return waitingUserRepository.findByUserId(userId)
                .map(WaitingUser::getId);
    }

    /**
     * 리더가 있는지 확인
     */
    public WaitingUserResponse getLeaderByOtt(OttType ott) {
        WaitingUser waitingUser = waitingUserRepository.findLeaderByOtt(ott)
                .orElseThrow(() -> new OttLeaderNotFoundException(ott));

        return WaitingUserResponse.from(waitingUser);
    }

    /**
     * 리더, OttType 반환
     */
    public Optional<IsLeaderAndOttResponse> getUserRoleAndOtt(Long userId) {
        return waitingUserRepository.findByUserId(userId)
                .map(waitingUser -> new IsLeaderAndOttResponse(
                        waitingUser.isLeader(),
                        waitingUser.getOtt()
                ));
    }

    /**
     * 리더가 아닌 user가 모두 있는지 확인
     */
    public List<WaitingUserResponse> getNoneLeaderByOtt(OttType ott) {
        int userCount = getNoneLeaderUserCount(ott);
        List<WaitingUser> waitingUsers = waitingUserRepository.findNoneLeaderByOtt(ott, userCount);

        if (waitingUsers.size() < userCount) {
            throw new NotOttLeaderException(ott);
        }

        return waitingUsers.stream()
                .map(WaitingUserResponse::from)
                .collect(Collectors.toList());
    }

    /**
     *  OttType 인원수 제한
     */
    private int getNoneLeaderUserCount(OttType ott) {
        return switch (ott) {
            case NETFLIX -> 2;
            case TVING, WAVVE -> 3;
            default -> throw new IllegalArgumentException("Unknown OttType: " + ott);
        };
    }
}