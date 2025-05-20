package seohan.ottshare.dto.waitingUserDto;

import lombok.*;
import seohan.ottshare.dto.userDto.UserResponse;
import seohan.ottshare.entity.User;
import seohan.ottshare.entity.WaitingUser;
import seohan.ottshare.enums.OttType;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WaitingUserResponse {

    private Long id;

    private User user;

    private String ottId;

    private String ottPassword;

    private OttType ottType;

    private boolean isLeader;

    public static WaitingUserResponse from(WaitingUser waitingUser) {
        return builder()
                .id(waitingUser.getId())
                .user(waitingUser.getUser())
                .ottId(waitingUser.getOttId())
                .ottPassword(waitingUser.getOttPassword())
                .ottType(waitingUser.getOtt())
                .isLeader(waitingUser.isLeader())
                .build();
    }
}