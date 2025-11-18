package org.example.scheduler.schedule.repository;

import org.example.scheduler.schedule.entity.Schedule;
import org.example.scheduler.util.exception.CustomException;
import org.example.scheduler.util.exception.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    void deleteAllByUser_Id(Long userId);

    List<Schedule> findSchedulesByUser_Id(Long userId);

    default Schedule findScheduleByIdOrThrow(Long scheduleId) {
        return findById(scheduleId).orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));
    }
}
