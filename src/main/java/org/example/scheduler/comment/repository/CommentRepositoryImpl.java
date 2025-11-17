package org.example.scheduler.comment.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.scheduler.comment.entity.QComment;
import org.example.scheduler.schedule.entity.QSchedule;

import java.util.List;

public class CommentRepositoryImpl implements CommentRepositoryCustom{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Tuple> countCommentsByScheduleIds(List<Long> scheduleIds) {
        QComment comment = QComment.comment;
        QSchedule schedule = QSchedule.schedule;

        JPAQuery<Tuple> query = new JPAQuery<>();

        return query.select(schedule.id,comment.count())
                .from(comment)
                .join(comment.schedule, schedule)
                .where(schedule.id.in(scheduleIds))
                .groupBy(schedule.id)
                .fetch();

    }
}
