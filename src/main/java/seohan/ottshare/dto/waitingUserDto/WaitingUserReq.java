package seohan.ottshare.dto.waitingUserDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import seohan.ottshare.dto.userDto.UserInfo;
import seohan.ottshare.enums.OttType;

@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class WaitingUserReq {

    private String ottId;

    private OttType ottType;

    private String ottPassword;

    private Boolean isLeader;

    private UserInfo userInfo;
}
