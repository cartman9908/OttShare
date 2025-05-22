package seohan.ottshare.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seohan.ottshare.dto.message.MessageRequest;
import seohan.ottshare.dto.message.MessageResponse;
import seohan.ottshare.dto.message.SendMessage;
import seohan.ottshare.dto.sharingUserDto.SharingUserResponse;
import seohan.ottshare.security.auth.CustomUserDetails;
import seohan.ottshare.service.RabbitMqService;
import seohan.ottshare.service.SharingUserService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageApiController {

    private final RabbitMqService rabbitMqService;
    private final SharingUserService sharingUserService;

    @PostMapping("/send/message")
    public ResponseEntity<String> sendMessage(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                              @RequestBody SendMessage sendMessage) {
        SharingUserResponse sharingUser = sharingUserService.getSharingUser(customUserDetails.getUserId());

        Long roomId = sharingUser.getOttShareRoom().getId();
        Long sharingUserId = sharingUser.getId();

        MessageRequest messageRequest = new MessageRequest(roomId, sharingUserId, sendMessage.getMessage());

        MessageResponse messageResponse = rabbitMqService.createMessage(messageRequest);

        rabbitMqService.sendMessage(messageResponse);
        return ResponseEntity.ok("Message sent to RabbitMQ");
    }
}