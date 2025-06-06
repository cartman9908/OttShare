package seohan.ottshare.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import seohan.ottshare.dto.ottShareRoomDto.OttShareRoomRequest;
import seohan.ottshare.dto.ottShareRoomDto.OttShareRoomResponse;

import seohan.ottshare.dto.waitingUserDto.IsLeaderAndOttResponse;
import seohan.ottshare.dto.waitingUserDto.WaitingUserReq;
import seohan.ottshare.dto.waitingUserDto.WaitingUserResponse;
import seohan.ottshare.entity.SharingUser;
import seohan.ottshare.security.auth.CustomUserDetails;
import seohan.ottshare.service.OttShareRoomService;
import seohan.ottshare.service.SharingUserService;
import seohan.ottshare.service.WaitingUserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/waiting-users")
@Slf4j
public class WaitingUserApiController {

    private final WaitingUserService waitingUserService;
    private final SharingUserService sharingUserService;
    private final OttShareRoomService ottShareRoomService;

    /**
     * user 저장
     */
    @PostMapping
    public ResponseEntity<String> createWaitingUser(@RequestBody WaitingUserReq waitingUserReq) {
        log.info("Save waiting user: {}", waitingUserReq.getUserInfo().getUsername());
        waitingUserService.createWaitingUser(waitingUserReq);
        createRoom(waitingUserReq);

        return ResponseEntity.ok("Room created successfully.");
    }

    /**
     * user 삭제
     */
    @DeleteMapping
    public ResponseEntity<String> deleteWaitingUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        waitingUserService.deleteUser(userDetails.getUserId());

        return ResponseEntity.ok("User Deleted Successfully.");
    }

    /**
     * userId로 user 조회
     */
    @PostMapping("/id")
    public ResponseEntity<Long> getWaitingUserId(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long waitingUserId = waitingUserService.getWaitingUserIdByUserId(userDetails.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found by Id" + userDetails.getUserId()));

        return ResponseEntity.ok(waitingUserId);
    }

    /**
     * 리더, ott 반환
     */
    @PostMapping("role-and-ott")
    public ResponseEntity<IsLeaderAndOttResponse> getUserRoleAndOtt(@AuthenticationPrincipal CustomUserDetails userDetails) {
        IsLeaderAndOttResponse isLeaderAndOttResponse = waitingUserService.getUserRoleAndOtt(userDetails.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found by Id" + userDetails.getUserId()));

        log.info("User Role And Ott: {} {}", isLeaderAndOttResponse.isLeader(), isLeaderAndOttResponse.getOttType());

        return ResponseEntity.ok(isLeaderAndOttResponse);
    }

    /**
     * 방 생성 메소드
     */
    //개선해야함(방을 만들기 전에 연관관계 설정 후 save 하는 방식으로 수정해야함)
    private void createRoom(WaitingUserReq waitingUserReq) {
        WaitingUserResponse leader = waitingUserService.getLeaderByOtt(waitingUserReq.getOttType());
        List<WaitingUserResponse> members = waitingUserService.getNoneLeaderByOtt(waitingUserReq.getOttType());

        members.add(leader);

        List<SharingUser> sharingUsers = sharingUserService.prepareSharingUserList(members);

        String ottId = leader.getOttId();
        String ottPassword = leader.getOttPassword();

        log.info("OttType: {}", leader.getOttType());
        OttShareRoomRequest ottShareRoomRequest = new OttShareRoomRequest(sharingUsers, leader.getOttType(), ottId, ottPassword);

        OttShareRoomResponse ottShareRoomResponse = ottShareRoomService.createOttShareRoom(ottShareRoomRequest);

        sharingUserService.assignRoomToSharingUsers(sharingUsers,ottShareRoomResponse);
        log.info("Room created successfully for OTT service: {}", ottShareRoomResponse.getId());

        waitingUserService.deleteUsers(members);
        sharingUserService.updateShareRoomStatus(sharingUsers);
    }
}
