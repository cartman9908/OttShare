package seohan.ottshare.dto.userDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import seohan.ottshare.enums.Role;
import seohan.ottshare.security.oauth.OAuth2UserInfo;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SocialUserRequest {

    String username;

    String password;

    String nickname;

    String email;

    Role role;

    public static SocialUserRequest from(OAuth2UserInfo oauthUserInfo) {
        return SocialUserRequest.builder()
                .username(oauthUserInfo.getProvider() + "_" + oauthUserInfo.getProviderId())
                .password(null)
                .nickname(oauthUserInfo.getName() + "_" + oauthUserInfo.getProviderId())
                .email(oauthUserInfo.getEmail())
                .role(Role.SOCIAL)
                .build();
    }
}
