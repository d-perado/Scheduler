package org.example.scheduler.comment.repository;

import com.querydsl.core.Tuple;

import java.util.List;

public interface CommentRepositoryCustom {
    List<Tuple> countCommentsByScheduleIds(List<Long> scheduleIds);
}
