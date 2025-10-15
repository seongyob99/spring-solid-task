package com.puzzlix.solid_task.domain.notification; // 폴더 구조에 맞게 패키지명 수정

import com.solapi.sdk.SolapiClient;
import com.solapi.sdk.message.exception.SolapiEmptyResponseException;
import com.solapi.sdk.message.exception.SolapiMessageNotReceivedException;
import com.solapi.sdk.message.exception.SolapiUnknownException;
import com.solapi.sdk.message.model.Message;
import com.solapi.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Component;

@Component
public class SmsNotificationSender implements NotificationSender{

    DefaultMessageService messageService =
            SolapiClient.INSTANCE
                    .createInstance("NCSY93BCTUCCLPLE", "LNPRJFWJ5N6S473988DSPCBXUJI7GFPE");


    @Override
    public void send(String message) {
        // SMS 외부 API 연동 기능 처리
        Message messageTo = new Message();
        messageTo.setFrom("010-6287-9963");
        messageTo.setTo("01052770535");
        messageTo.setText(message);
        System.out.println("SMS 알림 발송: " + message);
        try {
            messageService.send(messageTo);
        } catch (SolapiMessageNotReceivedException e) {
            throw new RuntimeException(e);
        } catch (SolapiEmptyResponseException e) {
            throw new RuntimeException(e);
        } catch (SolapiUnknownException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean supports(String type) {
        // SMS 라는 타입의 요청을 처리할 수 있다고 선언
        return "SMS".equalsIgnoreCase(type);
    }

}