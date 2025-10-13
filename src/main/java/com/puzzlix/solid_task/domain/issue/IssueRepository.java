package com.puzzlix.solid_task.domain.issue;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 저장소 역할(규칙을 정의하는 인터페이스
 */
public interface IssueRepository extends JpaRepository<Issue, Long> {
    // JpaRepository를 상속받으면 기본적인 많은 기능을 바로 제공
    // 메서드 선언해서 한방 쿼리 사용 -> N+1 문제 해결
}

