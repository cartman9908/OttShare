package seohan.ottshare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import seohan.ottshare.dto.userDto.SocialUserRequest;
import seohan.ottshare.dto.userDto.UserRequest;
import seohan.ottshare.enums.BankType;
import seohan.ottshare.enums.Role;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private SharingUser sharingUser;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private WaitingUser waitingUser;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number",nullable = false)
    private String phoneNumber;

    @Column(name = "account", nullable = false)
    private String account;

    @Column(name = "account_holder", nullable = false)
    private String accountHolder;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "bank_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private BankType bankType;

    @Column(name = "is_share_room", nullable = false, columnDefinition = "boolean default false")
    private boolean isShareRoom;

    public static User from(SocialUserRequest socialUserRequest) {
        return User.builder()
                .username(socialUserRequest.getUsername())
                .password(socialUserRequest.getPassword())
                .nickname(socialUserRequest.getNickname())
                .email(socialUserRequest.getEmail())
                .role(socialUserRequest.getRole())
                .build();
    }

    public static User from(UserRequest request) {
        return User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .nickname(request.getNickname())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .account(request.getAccount())
                .accountHolder(request.getAccountHolder())
                .role(request.getRole())
                .bankType(request.getBankType())
                .build();
    }

    public void update(String username, String password, String nickname, String email, String account, String accountHolder, BankType bankType) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.account = account;
        this.accountHolder = accountHolder;
        this.bankType = bankType;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void markAsSharingRoom() {
        this.isShareRoom = true;
    }

    public void kickShareRoom() {
        this.isShareRoom = false;
    }
}