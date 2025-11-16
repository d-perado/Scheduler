package org.example.scheduler.service;

import org.example.scheduler.dto.schedule.*;
import org.example.scheduler.entity.Schedule;
import lombok.RequiredArgsConstructor;
import org.example.scheduler.entity.User;
import org.example.scheduler.repository.CommentRepository;
import org.example.scheduler.repository.UserRepository;
import org.example.scheduler.util.Validator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.example.scheduler.repository.ScheduleRepository;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final Validator validator;
    private final CommentRepository commentRepository;

    @Transactional
    public ScheduleResponse createSchedule(CreateScheduleRequest request, Long userId) {
        User findedUser = validator.findUserByIdOrThrow(userId);

        Schedule schedule = new Schedule(request.getTitle(), request.getContent(), findedUser);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponse(savedSchedule);
    }



    @Transactional(readOnly = true)
    public ScheduleResponse getSchedule(Long scheduleId) {
        Schedule findedSchedule = validator.existScheduleById(scheduleId);

        return new ScheduleResponse(findedSchedule);
    }

    @Transactional
    public ScheduleResponse updateSchedule(Long userId, Long scheduleId, UpdateScheduleRequest request) {

        Schedule findedSchedule = validator.existScheduleById(scheduleId);

        validator.validateScheduleOwner(userId, findedSchedule);

        findedSchedule.modify(request.getTitle(), request.getContent());

        return new ScheduleResponse(findedSchedule);
    }

    @Transactional
    public void deleteSchedule(Long userId, Long scheduleId) {
        Schedule findedSchedule = validator.existScheduleById(scheduleId);

        validator.validateScheduleOwner(userId, findedSchedule);

        commentRepository.deleteAllBySchedule_Id(scheduleId);
        scheduleRepository.deleteById(scheduleId);
    }


    @Transactional(readOnly = true)
    public Page<PagedScheduleResponse> getPagedSchedule(int pageNo) {
        Page<Schedule> pagedSchedules = scheduleRepository
                .findAll(PageRequest.of(pageNo, 10, Sort.by(Sort.Direction.DESC, "updatedAt")));

        return pagedSchedules.map(x -> new PagedScheduleResponse(x, commentRepository.countCommentsBySchedule_Id(x.getId())));
    }
}
