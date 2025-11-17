package org.example.scheduler.comment.repository;

import org.example.scheduler.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
    List<Comment> findCommentsBySchedule_Id(Long scheduleId);

    Page<Comment> findCommentsBySchedule_Id(Long scheduleId, Pageable pageable);

    void deleteAllBySchedule_Id(Long scheduleId);
}
