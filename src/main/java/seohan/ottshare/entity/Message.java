package seohan.ottshare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import seohan.ottshare.dto.message.MessageRequest;
import seohan.ottshare.dto.message.MessageResponse;
import seohan.ottshare.dto.ottShareRoomDto.OttShareRoomResponse;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @Column(name = "message", nullable = false)
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sharing_user_id")
    private SharingUser sharingUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ott_share_room_id")
    private OttShareRoom ottShareRoom;

    public static Message from(MessageResponse response) {
        return Message.builder()
                .message(response.getMessage())
                .sharingUser(response.getSharingUser())
                .ottShareRoom(response.getOttShareRoom())
                .build();
    }
}
