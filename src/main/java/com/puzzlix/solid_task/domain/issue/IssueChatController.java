package com.puzzlix.solid_task.domain.issue;

import com.puzzlix.solid_task.domain.issue.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

// 테스트 (html 샘플코드)
@Controller //
@RequiredArgsConstructor
@EnableWebSocketMessageBroker // 허용 해야 SimpMessagingTemplate 가져옴
public class IssueChatController {

    // Stomp 메세지 템플릿
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * 클라이언트가 "/app/chat{issueId} 경로로 메세지를 보내면 이 메서드가 호출 됨.
     */
    @MessageMapping("/chat/{issueId}")
    public void handleChatMessage(@DestinationVariable Long issueId,
                                  ChatMessageDto messageDto) {

        // 이 경로를 구독하고 있는 클라이언트들에게 메세지를 전파
        messagingTemplate.convertAndSend("/topic/issues/" + issueId, messageDto);


    }
}
