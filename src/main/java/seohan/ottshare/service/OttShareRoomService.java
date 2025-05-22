package seohan.ottshare.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seohan.ottshare.dto.ottShareRoomDto.OttShareRoomIdAndPasswordResponse;
import seohan.ottshare.dto.ottShareRoomDto.OttShareRoomRequest;
import seohan.ottshare.dto.ottShareRoomDto.OttShareRoomResponse;
import seohan.ottshare.entity.OttShareRoom;
import seohan.ottshare.entity.SharingUser;
import seohan.ottshare.exception.NotFoundSharingUserForRoom;
import seohan.ottshare.exception.SharingUserNotCheckedException;
import seohan.ottshare.repository.OttShareRoomRepository;
import seohan.ottshare.repository.SharingUserRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class OttShareRoomService {

    private final OttShareRoomRepository ottShareRoomRepository;
    private final SharingUserRepository sharingUserRepository;
    private final EntityManager entityManager;

    /**
     * ott 공유방 생성
     */
    @Transactional
    public OttShareRoomResponse createOttShareRoom(OttShareRoomRequest ottShareRoomRequest) {
        OttShareRoom room = OttShareRoom.from(ottShareRoomRequest);
        return OttShareRoomResponse.from(ottShareRoomRepository.save(room));
    }

    /**
     * ott 공유방 삭제
     */
    @Transactional
    public void deleteOttShareRoom(Long userId, Long roomId) {

    }

    /**
     * ott 공유방 강제퇴장
     */
    @Transactional
    public void kickFromRoom(Long roomId, Long userId) {
        SharingUser sharingUser = sharingUserRepository.findByRoomIdAndUserId(roomId, userId)
                .orElseThrow(() -> new NotFoundSharingUserForRoom(roomId, userId));

        OttShareRoom ottShareRoom = sharingUser.getOttShareRoom();

        boolean isManaged = entityManager.contains(sharingUser);
        log.info("SharingUser is managed (영속 상태): {}", isManaged);

        ottShareRoom.removeSharingUser(sharingUser);
        sharingUser.getUser().kickShareRoom();
        sharingUserRepository.delete(sharingUser);
    }

    /**
     * 체크 기능
     */
    @Transactional
    public void updateCheckStatus(Long roomId, Long userId) {
        SharingUser sharingUser = sharingUserRepository.findByRoomIdAndUserId(roomId, userId)
                .orElseThrow(() -> new NotFoundSharingUserForRoom(roomId, userId));

        sharingUser.setChecked();
        log.info("Checked user with ID: {} in room ID: {}", userId, roomId);
    }

    /**
     * 아이디, 비밀번호 확인
     */
    public OttShareRoomIdAndPasswordResponse getRoomIdAndPassword(Long roomId, Long userId) {

        SharingUser sharingUser = sharingUserRepository.findByRoomIdAndUserId(roomId, userId)
                .orElseThrow(() -> new NotFoundSharingUserForRoom(roomId, userId));

        OttShareRoom ottShareRoom = sharingUser.getOttShareRoom();

        if(!sharingUser.isChecked()) {
            throw new SharingUserNotCheckedException(userId);
        }

        return new OttShareRoomIdAndPasswordResponse(ottShareRoom.getOttId(), ottShareRoom.getOttPassword());
    }

    /**
     * 새로운 맴버 찾기
     */
}
