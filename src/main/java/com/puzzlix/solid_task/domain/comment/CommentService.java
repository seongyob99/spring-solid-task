package com.puzzlix.solid_task.domain.comment;

import com.puzzlix.solid_task.domain.comment.dto.CommentRequest;
import com.puzzlix.solid_task.domain.comment.dto.CommentResponse;
import com.puzzlix.solid_task.domain.issue.Issue;
import com.puzzlix.solid_task.domain.issue.IssueRepository;
import com.puzzlix.solid_task.domain.user.Role;
import com.puzzlix.solid_task.domain.user.User;
import com.puzzlix.solid_task.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    // 댓글 작성
    public Comment createComment(Long issueId, CommentRequest request, String requestUserEmail) {

        User user = userRepository.findByEmail(requestUserEmail)
                .orElseThrow(() -> new NoSuchElementException("요청한 사용자를 찾을 수 없습니다."));

        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 이슈를 찾을 수 없습니다."));

        Comment newComment = new Comment();
        newComment.setContent(request.getContent());
        newComment.setIssue(issue);
        newComment.setWriter(user);

        return commentRepository.save(newComment);
    }

    // 댓글 조회
    public List<CommentResponse> findCommentsByIssue(Long issueId) {
        List<Comment> comments = commentRepository.findByIssueId(issueId);

        if (comments.isEmpty()) {
            throw new NoSuchElementException("해당 이슈에 등록된 댓글이 없습니다.");
        }

        return comments.stream()
                .map(CommentResponse::from)
                .toList();
    }

    // 댓글 수정
    public Comment updateComment(Long commentId, CommentRequest request, String requestUserEmail) {

        User requestUser = userRepository.findByEmail(requestUserEmail)
                .orElseThrow(() -> new NoSuchElementException("요청한 사용자를 찾을 수 없습니다."));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 댓글을 찾을 수 없습니다."));

        // 인가 처리 (작성자만 수정 가능)
        if (!comment.getWriter().getId().equals(requestUser.getId())) {
            throw new SecurityException("댓글을 수정할 권한이 없습니다.");
        }

        // 더티 체킹으로 자동 반영
        comment.setContent(request.getContent());

        return comment;
    }

    // 댓글 삭제
    public void deleteComment(Long commentId, String requestUserEmail) {

        User requestUser = userRepository.findByEmail(requestUserEmail)
                .orElseThrow(() -> new NoSuchElementException("요청한 사용자를 찾을 수 없습니다."));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 댓글을 찾을 수 없습니다."));

        boolean isWriter = comment.getWriter().getId().equals(requestUser.getId());
        boolean isAdmin = requestUser.getRole() == Role.ADMIN;

        if (!isWriter && !isAdmin) {
            throw new SecurityException("댓글을 삭제할 권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }
}
