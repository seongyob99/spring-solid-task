package com.puzzlix.solid_task.domain.user.login;


// 로그인 전략을 찾아주는 공장 클래스

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LoginStrategyFactory {
    // 로컬, 구글, 카카오...

    // 스프링이 @Component 로 등록된 모든 LoginStrategy 구현체 클래스들을 DI 처리해줌
    private final List<LoginStrategy> strategies;

    // 들어온 type 에 맞는 전략을 찾아서 반환 한다.
    public LoginStrategy findStrategy(String type) {
        // 1. 주입 받은 모든 구현 클래스들을 순회한다.
        for(LoginStrategy strategy : strategies) {
            // 2. 각 전략에게 "이 type 을 지원하는가?(LOCAL) -> true
            if(strategy.supports(type)) {
                // 구현 클래스를 반환한다 (LOCAL)
                return strategy;
            }
        }
        // 만약 for문이 다 돌떄 까지 지원하는 전략 구현 클래스를 찾지 못했다면 예외 발생
        throw new IllegalArgumentException("지원하지 않는 로그인 방식입니다.");
    }
}
