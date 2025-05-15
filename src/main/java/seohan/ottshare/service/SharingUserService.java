package seohan.ottshare.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seohan.ottshare.dto.waitingUserDto.WaitingUserResponse;
import seohan.ottshare.entity.SharingUser;
import seohan.ottshare.repository.SharingUserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SharingUserService {

    private final SharingUserRepository sharingUserRepository;

    @Transactional
    public List<SharingUser> prepareSharingUserList(List<WaitingUserResponse> waitingUserResponse) {
            return waitingUserResponse.stream()
                    .map(SharingUser::from)
                    .toList();
    }

    @Transactional
    public void updateShareRoomStatus(List<SharingUser> sharingUser) {
        List<SharingUser> sharingUsers = sharingUserRepository.findByIds(
                sharingUser.stream().map(SharingUser::getId).toList()
        );

        sharingUsers.forEach(members -> members.getUser().markAsSharingRoom());
    }
}