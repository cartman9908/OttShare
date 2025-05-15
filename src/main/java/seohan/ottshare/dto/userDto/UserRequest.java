package seohan.ottshare.dto.userDto;

import lombok.*;
import seohan.ottshare.enums.BankType;
import seohan.ottshare.enums.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    private String username;

    private String password;

    private String nickname;

    private String email;

    private String phoneNumber;

    private String account;

    private String accountHolder;

    private Role role;

    private BankType bankType;

    public static UserRequest from(UserInfo userInfo) {
        return UserRequest.builder()
                .username(userInfo.getUsername())
                .password(userInfo.getPassword())
                .nickname(userInfo.getNickname())
                .email(userInfo.getEmail())
                .phoneNumber(userInfo.getPhoneNumber())
                .account(userInfo.getAccount())
                .accountHolder(userInfo.getAccountHolder())
                .role(userInfo.getRole())
                .bankType(userInfo.getBankType())
                .build();
    }
}
