package seohan.ottshare.dto.userDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import seohan.ottshare.enums.BankType;

@Getter
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
