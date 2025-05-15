package seohan.ottshare.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seohan.ottshare.dto.ottShareRoomDto.OttShareRoomRequest;
import seohan.ottshare.dto.ottShareRoomDto.OttShareRoomResponse;
import seohan.ottshare.entity.OttShareRoom;
import seohan.ottshare.repository.OttShareRoomRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class OttShareRoomService {

    private final OttShareRoomRepository ottShareRoomRepository;

    /**
     * ott 공유방 생성
     */
    public OttShareRoomResponse createOttShareRoom(OttShareRoomRequest ottShareRoomRequest) {
        OttShareRoom room = OttShareRoom.from(ottShareRoomRequest);
        log.info("Saved OttShareRoom Id: {}", room.getId());
        return OttShareRoomResponse.from(ottShareRoomRepository.save(room));
    }

    /**
     * ott 공유방 삭제
     */


    /**
     * ott 공유방 강제퇴장
     */

    /**
     * 체크 기능
     */

    /**
     * 아이디, 비밀번호 확인
     */

    /**
     * 새로운 맴버 찾기
     */
}
