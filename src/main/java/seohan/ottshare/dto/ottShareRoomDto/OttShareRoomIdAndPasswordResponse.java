package seohan.ottshare.dto.ottShareRoomDto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OttShareRoomIdAndPasswordResponse {
    private String ottId;

    private String ottPassword;
}
