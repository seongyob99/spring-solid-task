package com.puzzlix.solid_task.domain.issue.dto;

import lombok.Getter;
import lombok.Setter;

public class IssueRequest {
    // 모바일에서 또는 클라이언트에서 - 이슈 생성 요청

    @Getter
    @Setter
    public static class Create {
        // 클라이언트가 직접 입력 해야하는 정보 또는 셋팅 되어야 하는 정보
        private String title;
        private String description;
        private Long projectId;
        private Long reporterId;
    } // end of static inner class

    // IssueRequest.Create.dto = new issueRequest.Create(...);
}
