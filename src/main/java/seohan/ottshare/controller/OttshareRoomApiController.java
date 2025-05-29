package seohan.ottshare.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seohan.ottshare.dto.ottShareRoomDto.OttShareRoomIdAndPasswordResponse;
import seohan.ottshare.dto.sharingUserDto.SharingUserResponse;
import seohan.ottshare.security.auth.CustomUserDetails;
import seohan.ottshare.service.OttShareRoomService;
import seohan.ottshare.service.SharingUserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ott-share-room")
@Slf4j
public class OttshareRoomApiController {

    private final OttShareRoomService ottShareRoomService;
    private final SharingUserService sharingUserService;

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
    @PostMapping("/{roomId}/user/{userId}/check")
    public ResponseEntity<String> checkUserInRoom(@PathVariable Long roomId
                                                , @PathVariable Long userId) {

        log.info("Checking user ID: {} in room ID: {}", userId, roomId);
        ottShareRoomService.updateCheckStatus(roomId, userId);

        return ResponseEntity.ok("User successfully checked in room");
    }

    /**
     * 아이디, 비밀번호 확인
     */
    @PostMapping("/id-password")
    public ResponseEntity<OttShareRoomIdAndPasswordResponse> getRoomIdAndPassword(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        SharingUserResponse sharingUser = sharingUserService.getSharingUser(customUserDetails.getUserId());
        Long roomId = sharingUser.getOttShareRoom().getId();

        log.info("Room ID: {}", roomId);

        OttShareRoomIdAndPasswordResponse ottShareRoomIdAndPasswordResponse = ottShareRoomService.getRoomIdAndPassword(roomId, customUserDetails.getUserId());


        return ResponseEntity.ok(ottShareRoomIdAndPasswordResponse);
    }

    /**
     * 새로운 맴버 찾기
     */
}