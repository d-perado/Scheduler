package org.example.scheduler.repository;

import org.example.scheduler.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    void deleteAllByUser_Id(Long userId);

    List<Schedule> findSchedulesByUser_Id(Long userId);
}
