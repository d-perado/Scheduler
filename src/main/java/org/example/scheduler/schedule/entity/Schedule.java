package org.example.scheduler.schedule.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.scheduler.util.TimeBaseEntity;
import org.example.scheduler.user.entity.User;

import java.util.List;

@Getter
@Entity
@Table(name = "schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends TimeBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false)
    private String title;

    @Column
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    public Schedule(String title, String content, User user) {
        super();
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void modify(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
