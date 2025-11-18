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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Tuple> countCommentsByScheduleIds(List<Long> scheduleIds) {
        QComment comment = QComment.comment;
        QSchedule schedule = QSchedule.schedule;

        JPAQuery<Tuple> query = new JPAQuery<>(entityManager);

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

    @Override
    public Page<Comment> findCommentsBySchedule_Id(Long scheduleId, Pageable pageable) {
        QComment comment = QComment.comment;
        QSchedule schedule = QSchedule.schedule;
        QUser user = QUser.user;

        List<Comment> comments = queryFactory.selectFrom(comment)
                .leftJoin(comment.user,user).fetchJoin()
                .leftJoin(comment.schedule,schedule).fetchJoin()
                .where(comment.schedule.id.eq(scheduleId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(comment.createdAt.desc()).fetch();

        Long pageSize = queryFactory
                .select(comment.count())
                .from(comment)
                .where(comment.schedule.id.eq(scheduleId))
                .fetchOne();

        pageSize = pageSize != null ? pageSize : 0L;

        return new PageImpl<>(comments,pageable,pageSize);
    }
}
