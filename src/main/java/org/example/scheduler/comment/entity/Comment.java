package org.example.scheduler.comment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.scheduler.schedule.entity.Schedule;
import org.example.scheduler.util.TimeBaseEntity;
import org.example.scheduler.user.entity.User;

@Getter
@Entity
@Table(name = "comments")
@NoArgsConstructor
public class Comment extends TimeBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    public Comment(String content, User user, Schedule schedule) {
        this.content = content;
        this.user = user;
        this.schedule = schedule;
    }

    public void modify(String content) {
        this.content = content;
    }
}
