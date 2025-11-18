package org.example.scheduler.comment.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.example.scheduler.comment.entity.Comment;
import org.example.scheduler.comment.entity.QComment;
import org.example.scheduler.schedule.entity.QSchedule;
import org.example.scheduler.user.entity.QUser;

import java.util.List;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Tuple> countCommentsByScheduleIds(List<Long> scheduleIds) {
        QComment comment = QComment.comment;
        QSchedule schedule = QSchedule.schedule;

        JPAQuery<Tuple> query = new JPAQuery<>(em);

        return query.select(schedule.id, comment.count())
                .from(comment)
                .join(comment.schedule, schedule)
                .where(schedule.id.in(scheduleIds))
                .groupBy(schedule.id)
                .fetch();

    }

    @Override
    public List<Comment> findCommentsWithUserAndSchedule(Long scheduleId) {
        QComment comment = QComment.comment;
        QUser user = QUser.user;
        QSchedule schedule = QSchedule.schedule;

        return queryFactory.selectFrom(comment)
                .leftJoin(comment.user, user).fetchJoin()
                .leftJoin(comment.schedule, schedule).fetchJoin()
                .where(comment.schedule.id.eq(scheduleId))
                .fetch();
    }
}
