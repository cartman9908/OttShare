package seohan.ottshare.dto.userDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import seohan.ottshare.entity.User;
import seohan.ottshare.enums.BankType;
import seohan.ottshare.enums.Role;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long userId;

    private String username;

    private String password;

    private String nickname;

    private String email;

    private String phoneNumber;

    private String account;

    private String accountHolder;

    private Role role;

    private BankType bankType;

    private boolean isShareRoom;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .account(user.getAccount())
                .accountHolder(user.getAccountHolder())
                .role(user.getRole())
                .bankType(user.getBankType())
                .build();
    }
}