package com.puzzlix.solid_task.domain.user.login;

import com.puzzlix.solid_task.domain.issue.dto.UserRequest;
import com.puzzlix.solid_task.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoogleLoginStrategy implements LoginStrategy {

    @Override
    public User login(UserRequest.Login request) {
        // TODO 도전과제
        // 1. 클라이언트에게 받은 구글 토큰을 가져옴
        // 2. 구글 서버로부터 사용자 정보 (이메일 이름)
        // 3. 해당 이메일을 db 조회 후 없으면 자동 회원 가입. 있으면 로그인 처리 및 업데이트
        return null;
    }

    @Override
    public boolean supports(String type) {
        return "GOOGLE".equalsIgnoreCase(type);
    }
}
