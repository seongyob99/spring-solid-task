package com.puzzlix.solid_task.domain.comment.dto;

import com.puzzlix.solid_task.domain.comment.Comment;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CommentResponse {
    private Long id;
    private String content;
    private String writerName;


    public static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .writerName(comment.getWriter().getName())
                .build();
    }
}
