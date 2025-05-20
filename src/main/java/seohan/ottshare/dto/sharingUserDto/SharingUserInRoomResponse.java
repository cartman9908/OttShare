package seohan.ottshare.dto.sharingUserDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import seohan.ottshare.dto.userDto.UserResponse;
import seohan.ottshare.entity.SharingUser;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SharingUserInRoomResponse {

    private Long id;

    private UserResponse user;

    private boolean isLeader;

    private boolean isChecked;

    public static SharingUserInRoomResponse from(SharingUser sharingUser) {
        return SharingUserInRoomResponse.builder()
                .id(sharingUser.getId())
                .user(UserResponse.from(sharingUser.getUser()))
                .isLeader(sharingUser.isLeader())
                .isChecked(sharingUser.isChecked())
                .build();
    }
}

