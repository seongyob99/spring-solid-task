package com.puzzlix.solid_task.domain.issue;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemoryIssueRepository implements IssueRepository {

    // 동시성 문제 방지 ConcurrentHashMap 사용
    private static Map<Long, Issue> store = new ConcurrentHashMap<>();
    private static AtomicLong sequence = new AtomicLong(0);

    @Override
    public Issue save(Issue issue) {
        // save 요청시 Issue에 상태값 id가 없는 상태
        if(issue.getId() == null) {
            // -> 1 변경하고 issue 객체에 상태값 id를 1로 할당
            issue.setId(sequence.incrementAndGet());
            // sequence -> 1로 결정됨
            // sequence -> 2로 결정됨
        }

        store.put(issue.getId(), issue);
        return issue;
    }

    @Override
    public Optional<Issue> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Issue> findAll() {
        return new ArrayList<>(store.values());
    }
}
