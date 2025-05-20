package seohan.ottshare.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seohan.ottshare.dto.ottShareRoomDto.OttShareRoomResponse;
import seohan.ottshare.dto.waitingUserDto.WaitingUserResponse;
import seohan.ottshare.entity.OttShareRoom;
import seohan.ottshare.entity.SharingUser;
import seohan.ottshare.repository.OttShareRoomRepository;
import seohan.ottshare.repository.SharingUserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SharingUserService {

    private final SharingUserRepository sharingUserRepository;
    private final OttShareRoomRepository ottShareRoomRepository;

    @Transactional
    public List<SharingUser> prepareSharingUserList(List<WaitingUserResponse> waitingUserResponse) {
            return waitingUserResponse.stream()
                    .map(SharingUser::from)
                    .collect(Collectors.toList());
    }

    @Transactional
    public void updateShareRoomStatus(List<SharingUser> sharingUser) {
        List<SharingUser> sharingUsers = sharingUserRepository.findByIds(
                sharingUser.stream().map(SharingUser::getId).collect(Collectors.toList())
        );

        sharingUsers.forEach(members -> members.getUser().markAsSharingRoom());
    }

    @Transactional
    public void assignRoomToSharingUsers(List<SharingUser> sharingUsers, OttShareRoomResponse ottShareRoomResponse) {
        OttShareRoom room = ottShareRoomRepository.findById(ottShareRoomResponse.getId())
                .orElseThrow(() -> new IllegalArgumentException("Room not found with id: " + ottShareRoomResponse.getId()));

        sharingUsers.forEach(sharingUser -> sharingUser.changeOttShareRoom(room));
    }
}