package seohan.ottshare.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import seohan.ottshare.dto.ottShareRoomDto.OttShareRoomResponse;
import seohan.ottshare.dto.sharingUserDto.SharingUserResponse;
import seohan.ottshare.entity.Message;
import seohan.ottshare.entity.OttShareRoom;
import seohan.ottshare.entity.SharingUser;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {

    private OttShareRoomResponse ottShareRoomResponse;

    private SharingUserResponse sharingUserResponse;

    private String message;

    public static MessageResponse from(Message message) {
        return MessageResponse.builder()
                .ottShareRoomResponse(OttShareRoomResponse.from(message.getOttShareRoom()))
                .sharingUserResponse(SharingUserResponse.from(message.getSharingUser()))
                .message(message.getMessage())
                .build();
    }
}
