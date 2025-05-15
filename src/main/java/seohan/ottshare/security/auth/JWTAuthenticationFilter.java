package seohan.ottshare.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import seohan.ottshare.dto.userDto.LoginUserRequest;
import seohan.ottshare.service.TokenBlackListService;
import seohan.ottshare.util.JWTUtil;

import java.io.IOException;

@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final TokenBlackListService tokenBlackListService;


    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, TokenBlackListService tokenBlackList, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.tokenBlackListService = tokenBlackList;
        // users/api/login url 설정
        setFilterProcessesUrl("/api/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try{
            // 로그인 시 DB 에서 사용자 조회 후 인증객체 반환
            LoginUserRequest loginUserRequest = new ObjectMapper().readValue(request.getInputStream(), LoginUserRequest.class);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginUserRequest.getUsername(), loginUserRequest.getPassword());
            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResults) throws IOException {
        String accessToken = jwtUtil.createToken(authResults.getName());

        response.addHeader("Authorization", "Bearer " + accessToken);

        if(tokenBlackListService.isBlackList(accessToken)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        // refreshToken 생성 및 쿠키에 추가
        String refreshToken = jwtUtil.createRefreshToken(authResults.getName());
        Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setMaxAge(Math.toIntExact(jwtUtil.getRefreshExpiration()));
        response.addCookie(refreshTokenCookie);

        // 프로트엔드 응답 바디에 추가
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{ \"access_token\": \"" + accessToken + "\" }");
    }
}