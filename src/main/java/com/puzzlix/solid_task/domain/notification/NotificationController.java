package com.puzzlix.solid_task.domain.notification;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    private final SmsNotificationSender smsNotificationSender;

    // 생성자 주입 (Dependency Injection)
    public NotificationController(SmsNotificationSender smsNotificationSender) {
        this.smsNotificationSender = smsNotificationSender;
    }

    /**
     * http://localhost:8080/notifications/send/sms 로 요청하면 SMS 발송을 시도합니다.
     */
    @GetMapping("/notifications/send/sms")
    public String sendTestSms() {
        // 실제 NotificationSender의 send() 메서드는 String message를 인수로 받습니다.
        // 여기서는 임시 테스트 메시지를 전달합니다.
        String testMessage = "문자 테스트 내용";

        try {
            // SmsNotificationSender의 send 메서드 호출
            // 이 메서드 내부에서 CoolSMS(Solapi) API를 통해 문자 발송이 이루어집니다.
            smsNotificationSender.send(testMessage);

            // 발송 성공 요청 응답
            return "✅ **SMS 발송 요청 성공!** (메시지: '" + testMessage + "') 수신 휴대폰을 확인해 주세요.";

        } catch (Exception e) {
            // 발송 실패 시 예외 처리
            System.err.println("SMS 발송 요청 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            return "❌ **SMS 발송 요청 실패!** 오류: " + e.getMessage();
        }
    }
}