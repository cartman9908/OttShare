package seohan.ottshare.dto.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import seohan.ottshare.dto.ottShareRoomDto.OttShareRoomResponse;
import seohan.ottshare.dto.sharingUserDto.SharingUserInRoomResponse;
import seohan.ottshare.dto.sharingUserDto.SharingUserResponse;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {

    private Long roomId;

    private Long userId;

    private String message;
}
