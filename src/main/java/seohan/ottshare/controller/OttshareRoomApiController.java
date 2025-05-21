package seohan.ottshare.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seohan.ottshare.service.OttShareRoomService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ott-share-room")
@Slf4j
public class OttshareRoomApiController {

    private final OttShareRoomService ottShareRoomService;

    /**
     * 채팅방
     */

    /**
     * 강제퇴장
     */
    @PostMapping("/rooms/{roomId}/user/{userId}")
    public ResponseEntity<String> kickUserFromRoom(@PathVariable Long roomId,
                                                   @PathVariable Long userId) {

        log.info("Kicking user ID: {} from room ID: {}", userId, roomId);
        ottShareRoomService.kickFromRoom(roomId, userId);

        return ResponseEntity.ok("User successfully kicked from room");
    }

    /**
     * 스스로 채팅방 나가기
     */

    /**
     * 체크
     */

    /**
     * 아이디, 비밀번호 확인
     */

    /**
     * 새로운 맴버 찾기
     */
}