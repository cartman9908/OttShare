package seohan.ottshare.dto.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import seohan.ottshare.entity.OttShareRoom;
import seohan.ottshare.entity.SharingUser;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {

    private OttShareRoom ottShareRoom;

    private SharingUser sharingUser;

    private String message;
}
