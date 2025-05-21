package seohan.ottshare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import seohan.ottshare.dto.ottShareRoomDto.OttShareRoomRequest;
import seohan.ottshare.enums.OttType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ott_share_room")
public class OttShareRoom extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ott_share_room_id")
    private Long id;

    @Builder.Default
    @OneToMany(mappedBy = "ottShareRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SharingUser> sharingUsers = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "ottShareRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "ott", nullable = false)
    private OttType ott;

    @Column(name = "ott_id", nullable = false)
    private String ottId;

    @Column(name = "ott_password", nullable = false)
    private String ottPassword;

    public static OttShareRoom from(OttShareRoomRequest ottShareRoomRequest) {
        return OttShareRoom.builder()
                .sharingUsers(ottShareRoomRequest.getShareUsers())
                .ott(ottShareRoomRequest.getOttType())
                .ottId(ottShareRoomRequest.getOttId())
                .ottPassword(ottShareRoomRequest.getOttPassword())
                .build();
    }

    public void removeSharingUser(SharingUser sharingUser) {
        this.sharingUsers.remove(sharingUser);
        sharingUser.removeRoom();
    }
}
