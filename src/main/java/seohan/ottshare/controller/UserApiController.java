package seohan.ottshare.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import seohan.ottshare.dto.userDto.*;
import seohan.ottshare.security.auth.CustomUserDetails;
import seohan.ottshare.service.TokenBlackListService;
import seohan.ottshare.service.UserService;
import seohan.ottshare.util.JWTUtil;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Slf4j
public class UserApiController {

    private final JWTUtil jwtUtil;
    private final TokenBlackListService tokenBlackListService;
    private final UserService userService;

    /**
     * 회원가입
     */
    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.createUser(userRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    /**
     * 회원정보 수정
     */
    @PostMapping("/edit")
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                        @RequestBody UserUpdateReq updateReq) {
        userService.updateUser(customUserDetails.getUserId(), updateReq);
        UserResponse updatedUser = userService.getUser(customUserDetails.getUserId());

        return ResponseEntity.ok(updatedUser);
    }

    /**
     * 임시 비밀번호 발급
     */
    @PostMapping("/find-password")
    public ResponseEntity<Object> resetPassword(@RequestBody FindPasswordRequest dto) {
        UserResponse userResponse = userService.findUserForPasswordReset(dto.getUserId(), dto.getUsername());
        String temporaryPassword = PasswordGenerator.generatePassword(10);
        userService.updatePassword(userResponse.getUserId(), temporaryPassword);
        FindPasswordResponse response = new FindPasswordResponse("임시 비밀번호는" + temporaryPassword + "입니다.");

        return ResponseEntity.ok(response);
    }

    /**
     * user 삭제
     */
    @DeleteMapping
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.deleteUser(userDetails.getUserId());

        return ResponseEntity.ok("User Deleted Successfully" + userDetails.getUserId());
    }

    /**
     * 로그아웃
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String accessToken = authHeader.substring(7);
            long expiration = jwtUtil.extractAllClaims(accessToken).getExpiration().getTime() - System.currentTimeMillis();

            tokenBlackListService.addToBlackList(accessToken, expiration);
        }
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().body("로그아웃 완료");
    }

    /**
     * accessToken 재발급
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshAccessToken(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token failed");
        }

        String refreshToken = Arrays.stream(cookies)
                .filter(cookie -> "refresh_token".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(null);

        if(refreshToken == null || jwtUtil.isTokenExpired(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token expired");
        }

        if(tokenBlackListService.isBlackList(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Blacklist refresh token expired");
        }

        String username = jwtUtil.extractUsername(refreshToken);
        String newAccessToken = jwtUtil.createToken(username);

        return ResponseEntity.ok("{\"newAccessToken\": \"" + newAccessToken + "\"}");
    }
}