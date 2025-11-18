package org.example.scheduler.comment.repository;

import org.example.scheduler.comment.entity.Comment;
import org.example.scheduler.util.exception.CustomException;
import org.example.scheduler.util.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
    Page<Comment> findCommentsBySchedule_Id(Long scheduleId, Pageable pageable);

    void deleteAllBySchedule_Id(Long scheduleId);

    default Comment findCommentByIdOrThrow(Long id) {
        return findById(id).orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
    }
}
