package org.example.scheduler.schedule.service;

import com.querydsl.core.Tuple;
import org.example.scheduler.schedule.entity.Schedule;
import lombok.RequiredArgsConstructor;
import org.example.scheduler.user.entity.User;
import org.example.scheduler.comment.repository.CommentRepository;
import org.example.scheduler.schedule.dto.CreateScheduleRequest;
import org.example.scheduler.schedule.dto.PagedScheduleResponse;
import org.example.scheduler.schedule.dto.ScheduleResponse;
import org.example.scheduler.schedule.dto.UpdateScheduleRequest;
import org.example.scheduler.util.Validator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.example.scheduler.schedule.repository.ScheduleRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final Validator validator;
    private final CommentRepository commentRepository;

    @Transactional
    public ScheduleResponse createSchedule(CreateScheduleRequest request, Long userId) {
        User foundUser = validator.findUserByIdOrThrow(userId);

        Schedule schedule = new Schedule(request.getTitle(), request.getContent(), foundUser);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponse(savedSchedule);
    }

    @Transactional(readOnly = true)
    public ScheduleResponse getSchedule(Long scheduleId) {
        Schedule foundSchedule = validator.existScheduleById(scheduleId);

        return new ScheduleResponse(foundSchedule);
    }

    @Transactional
    public ScheduleResponse updateSchedule(Long userId, Long scheduleId, UpdateScheduleRequest request) {

        Schedule foundSchedule = validator.existScheduleById(scheduleId);

        validator.validateScheduleOwner(userId, foundSchedule);

        foundSchedule.modify(request.getTitle(), request.getContent());

        return new ScheduleResponse(foundSchedule);
    }

    @Transactional
    public void deleteSchedule(Long userId, Long scheduleId) {
        Schedule foundSchedule = validator.existScheduleById(scheduleId);

        validator.validateScheduleOwner(userId, foundSchedule);

        commentRepository.deleteAllBySchedule_Id(scheduleId);
        scheduleRepository.deleteById(scheduleId);
    }

    @Transactional(readOnly = true)
    public Page<PagedScheduleResponse> getPagedSchedule(int pageNo) {
        List<Schedule> pagedSchedules = scheduleRepository
                .findAll(PageRequest.of(pageNo, 10, Sort.by(Sort.Direction.DESC, "updatedAt"))).getContent();

        List<Long> scheduleIds = pagedSchedules.stream()
                .map(Schedule::getId)
                .collect(Collectors.toList());

        List<Tuple> commentCounts = commentRepository.countCommentsByScheduleIds(scheduleIds);

        Map<Long, Long> commentCountMap = commentCounts.stream()
                .collect(Collectors.toMap(tuple -> tuple.get(0, Long.class), tuple -> tuple.get(1, Long.class)));

        List<PagedScheduleResponse> response = pagedSchedules.stream()
                .map(scheduleItem -> new PagedScheduleResponse(scheduleItem, Math.toIntExact(commentCountMap.get(scheduleItem.getId()))))
                .collect(Collectors.toList());

        return new PageImpl<>(response, PageRequest.of(pageNo, 10), pagedSchedules.size());
    }
}
