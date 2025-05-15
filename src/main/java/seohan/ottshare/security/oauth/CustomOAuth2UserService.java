package seohan.ottshare.security.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seohan.ottshare.dto.userDto.SocialUserRequest;
import seohan.ottshare.entity.User;
import seohan.ottshare.repository.UserRepository;
import seohan.ottshare.security.auth.CustomUserDetails;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuth2UserInfo oAuth2UserInfo = getOAuth2UserInfo(registrationId, attributes);

        User user = createUser(oAuth2UserInfo);

        if (!userRepository.existsByUsername(user.getUsername())) {
            userRepository.save(user);
        } else {
            updateUser(user);
        }

        return new CustomUserDetails(user, attributes);
    }

    private OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equals("google")) {
            return new GoogleUserInfo(attributes);
        } else if (registrationId.equals("kakao")) {
            return new KakaoUserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationException("Unsupported registration id " + registrationId);
        }
    }

    private User createUser(OAuth2UserInfo oAuth2UserInfo) {

        return User.from(SocialUserRequest.from(oAuth2UserInfo));
    }

    private void updateUser(User user) {
        userRepository.save(user);
    }
}
