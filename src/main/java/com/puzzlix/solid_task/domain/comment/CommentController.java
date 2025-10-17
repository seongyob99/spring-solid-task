package com.puzzlix.solid_task.domain.comment;

import com.puzzlix.solid_task.domain.comment.dto.CommentRequest;
import com.puzzlix.solid_task.domain.comment.dto.CommentResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 댓글 조회
    @GetMapping("/issues/{issueId}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long issueId) {
        return ResponseEntity.ok(commentService.findCommentsByIssue(issueId));
    }


    // 댓글 작성
    @PostMapping("issues/{issueId}/comments")
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable Long issueId,
            @RequestBody CommentRequest request,
            HttpServletRequest httpRequest
    ) {
        String userEmail = (String) httpRequest.getAttribute("userEmail");
        Comment comment = commentService.createComment(issueId, request, userEmail);
        return ResponseEntity.ok(CommentResponse.from(comment));
    }

    // 댓글 수정
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequest request,
            HttpServletRequest httpRequest
    ) {
        String userEmail = (String) httpRequest.getAttribute("userEmail");
        Comment comment = commentService.updateComment(commentId, request, userEmail);
        return ResponseEntity.ok(CommentResponse.from(comment));
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            HttpServletRequest httpRequest
    ) {
        String userEmail = (String) httpRequest.getAttribute("userEmail");
        commentService.deleteComment(commentId, userEmail);
        return ResponseEntity.noContent().build();
    }
}
