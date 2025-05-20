package seohan.ottshare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import seohan.ottshare.dto.userDto.UserResponse;
import seohan.ottshare.dto.waitingUserDto.WaitingUserResponse;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sharing_user")
public class SharingUser extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sharing_user_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ott_sharing_room_id")
    private OttShareRoom ottShareRoom;

    @OneToMany(mappedBy = "sharingUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> message = new ArrayList<>();

    @Column(name = "is_leader", nullable = false)
    private boolean isLeader;

    @Column(name = "is_checked", nullable = false, columnDefinition = "boolean default false")
    private boolean isChecked;

    public static SharingUser from(WaitingUserResponse waitingUserResponse){
        return SharingUser.builder()
                .user(waitingUserResponse.getUser())
                .isLeader(waitingUserResponse.isLeader())
                .ottShareRoom(null)
                .build();
    }

    public void changeOttShareRoom(OttShareRoom ottShareRoom) {
        this.ottShareRoom = ottShareRoom;
    }
}
