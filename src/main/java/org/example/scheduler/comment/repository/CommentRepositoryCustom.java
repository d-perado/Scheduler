package org.example.scheduler.comment.repository;

import com.querydsl.core.Tuple;
import org.example.scheduler.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentRepositoryCustom {
    List<Tuple> countCommentsByScheduleIds(List<Long> scheduleIds);

    List<Comment> findCommentsWithUserAndSchedule(Long scheduleId);

    Page<Comment> findCommentsBySchedule_Id(Long scheduleId, Pageable pageable);
}
