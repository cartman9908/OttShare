package seohan.ottshare.dto.sharingUserDto;

import lombok.*;
import seohan.ottshare.dto.ottShareRoomDto.OttShareRoomResponse;
import seohan.ottshare.dto.userDto.UserResponse;
import seohan.ottshare.entity.SharingUser;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SharingUserResponse {

    private Long id;

    private UserResponse user;

    private OttShareRoomResponse ottShareRoom;

    private boolean isLeader;

    private boolean isChecked;

    public static SharingUserResponse from(SharingUser sharingUser) {
        return SharingUserResponse.builder()
                .id(sharingUser.getId())
                .user(UserResponse.from(sharingUser.getUser()))
                .ottShareRoom(OttShareRoomResponse.from(sharingUser.getOttShareRoom()))
                .isLeader(sharingUser.isLeader())
                .isChecked(sharingUser.isChecked())
                .build();
    }
}
