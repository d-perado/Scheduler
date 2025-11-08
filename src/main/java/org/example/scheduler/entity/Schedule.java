package org.example.scheduler.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends TimeBaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String writer;

    public Schedule(String title, String content, String writer) {
        super();
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public void modify(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
