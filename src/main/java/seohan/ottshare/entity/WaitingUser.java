package seohan.ottshare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import seohan.ottshare.dto.waitingUserDto.WaitingUserReq;
import seohan.ottshare.enums.OttType;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "waiting_user")
@Builder
public class WaitingUser extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "waiting_user_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "ott_id")
    private String ottId;

    @Column(name = "ott_password")
    private String ottPassword;

    @Enumerated(EnumType.STRING)
    @Column(name = "ott", nullable = false)
    private OttType ott;

    @Column(name = "is_leader", nullable = false)
    private boolean isLeader;

    public static WaitingUser from(WaitingUserReq waitingUserReq, User user) {
        return WaitingUser.builder()
                .user(user)
                .ott(waitingUserReq.getOttType())
                .ottId(waitingUserReq.getOttId())
                .ottPassword(waitingUserReq.getOttPassword())
                .isLeader((waitingUserReq.getIsLeader()))
                .build();
    }
}
