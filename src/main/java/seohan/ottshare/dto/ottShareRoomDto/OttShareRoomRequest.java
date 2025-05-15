package seohan.ottshare.dto.ottShareRoomDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import seohan.ottshare.entity.SharingUser;
import seohan.ottshare.enums.OttType;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OttShareRoomRequest {

    private List<SharingUser> shareUsers = new ArrayList<>();

    private OttType ottType;

    private String ottId;

    private String ottPassword;
}
