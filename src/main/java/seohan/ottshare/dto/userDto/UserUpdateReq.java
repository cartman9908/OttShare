package seohan.ottshare.dto.userDto;

import lombok.*;
import seohan.ottshare.enums.BankType;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateReq {

    private Long id;

    private String username;

    private String password;

    private String nickname;

    private String email;

    private String account;

    private String accountHolder;

    private BankType bankType;
}
