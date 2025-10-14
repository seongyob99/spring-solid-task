
package com.puzzlix.solid_task._global.config.jwt;

import com.puzzlix.solid_task.domain.user.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 요청 메세지 헤더에서 키값 (Authorization) 헤더를 찾아 JWT 토큰들 추출
        // 2. 순수 토큰 추출해서
        String token = resolveToken(request);
        if(token != null && jwtTokenProvider.validateToken(token)) {
            // 결과값이 true 라면 controller 로 보낸다

            // request 이메일 정보
            String userEmail = jwtTokenProvider.getSubject(token);
            // request role
            Role userRole = jwtTokenProvider.getRole(token);

            request.setAttribute("userEmail", userEmail);
            request.setAttribute("userEmail", userRole);
            return true;
        }

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 토큰입니다");
        return false;
    }


    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        // 토큰 검증 및 "Bearer " (공백 한칸을 잘라내자)
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}

