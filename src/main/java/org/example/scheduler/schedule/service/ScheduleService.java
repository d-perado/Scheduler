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
import org.example.scheduler.user.repository.UserRepository;
import org.example.scheduler.util.Validator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.example.scheduler.schedule.repository.ScheduleRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;
    private final Validator validator;

    @Transactional
    public ScheduleResponse createSchedule(CreateScheduleRequest request, Long userId) {
        User foundUser = userRepository.findUserByIdOrThrow(userId);

        Schedule schedule = new Schedule(request.getTitle(), request.getContent(), foundUser);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponse(savedSchedule);
    }

    @Transactional(readOnly = true)
    public ScheduleResponse getSchedule(Long scheduleId) {
        Schedule foundSchedule = scheduleRepository.findScheduleByIdOrThrow(scheduleId);

        return new ScheduleResponse(foundSchedule);
    }

    @Transactional
    public ScheduleResponse updateSchedule(Long userId, Long scheduleId, UpdateScheduleRequest request) {
        Schedule foundSchedule = scheduleRepository.findScheduleByIdOrThrow(scheduleId);

        validator.validateScheduleOwner(userId, foundSchedule);

        foundSchedule.modify(request.getTitle(), request.getContent());

        return new ScheduleResponse(foundSchedule);
    }

    @Transactional
    public void deleteSchedule(Long userId, Long scheduleId) {
        Schedule foundSchedule = scheduleRepository.findScheduleByIdOrThrow(scheduleId);

        validator.validateScheduleOwner(userId, foundSchedule);

        commentRepository.deleteAllBySchedule_Id(scheduleId);
        scheduleRepository.deleteById(scheduleId);
    }

    @Transactional(readOnly = true)
    public Page<PagedScheduleResponse> getPagedSchedule(int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "updatedAt"));

        Page<Schedule> schedulePage = scheduleRepository.findAll(pageRequest);

        List<Schedule> pagedSchedules = schedulePage.getContent();

        if (pagedSchedules.isEmpty()) { //데이터 없으면 빈페이지
            return new PageImpl<>(Collections.emptyList(), pageRequest, schedulePage.getTotalElements());
        }

        List<Long> scheduleIds = pagedSchedules.stream()
                .map(Schedule::getId)
                .collect(Collectors.toList());

        List<Tuple> commentCounts = commentRepository.countCommentsByScheduleIds(scheduleIds);

        Map<Long, Long> commentCountMap = commentCounts.stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(0, Long.class),
                        tuple -> tuple.get(1, Long.class)
                ));

        List<PagedScheduleResponse> response = pagedSchedules.stream()
                .map(scheduleItem -> {
                    long count = commentCountMap.getOrDefault(scheduleItem.getId(), 0L);//댓글갯수 0일때에도 값 넣어주기
                    return new PagedScheduleResponse(scheduleItem, Math.toIntExact(count));
                })
                .collect(Collectors.toList());

        return new PageImpl<>(response, pageRequest, schedulePage.getTotalElements());
    }

}
