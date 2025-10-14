package com.puzzlix.solid_task.domain.user;

import com.puzzlix.solid_task._global.config.jwt.JwtTokenProvider;
import com.puzzlix.solid_task._global.dto.CommonResponseDto;
import com.puzzlix.solid_task.domain.issue.dto.UserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto<?>> signUp(@Valid @RequestBody UserRequest.SignUp request) {
        userService.signUp(request);
        return ResponseEntity.ok(CommonResponseDto.success(null, "회원가입이 완료 되었습니다."));
    }

    @PostMapping("/login/{type}")
    public ResponseEntity<CommonResponseDto<?>> login(
            @PathVariable(name = "type") String type,
            @Valid @RequestBody UserRequest.Login request) {
        System.out.println(request.getEmail());
        System.out.println(request.getPassword());
        User user = userService.login(type, request);
        // 사용자 이메일을 기반으로 JWT 토큰 생성
        String token = jwtTokenProvider.createToken(user.getEmail());
        return ResponseEntity.ok(CommonResponseDto.success(token, "로그인에 성공했습니다"));
    }
}
