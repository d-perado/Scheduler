package org.example.scheduler.repository;

import org.example.scheduler.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsBySchedule_Id(Long scheduleId);

    Page<Comment> findCommentsBySchedule_Id(Long scheduleId, Pageable pageable);
}
