package seohan.ottshare.dto.ottShareRoomDto;

import lombok.*;
import seohan.ottshare.dto.sharingUserDto.SharingUserInRoomResponse;
import seohan.ottshare.dto.sharingUserDto.SharingUserResponse;
import seohan.ottshare.entity.Message;
import seohan.ottshare.entity.OttShareRoom;
import seohan.ottshare.enums.OttType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class OttShareRoomResponse {

    private Long id;

    private List<SharingUserInRoomResponse> sharingUserInRoomResponses = new ArrayList<>();

    private List<Message> messages = new ArrayList<>();

    private OttType ottType;

    private String ottId;

    private String ottPassword;

    public static OttShareRoomResponse from(OttShareRoom ottShareRoom) {
        return OttShareRoomResponse.builder()
                .id(ottShareRoom.getId())
                .sharingUserInRoomResponses(ottShareRoom.getSharingUsers().stream()
                        .map(SharingUserInRoomResponse::from)
                        .collect(Collectors.toList()))
                .ottType(ottShareRoom.getOtt())
                .ottId(ottShareRoom.getOttId())
                .ottPassword(ottShareRoom.getOttPassword())
                .build();
    }
}
