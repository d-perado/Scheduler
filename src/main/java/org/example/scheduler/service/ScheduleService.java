package org.example.scheduler.service;

import org.example.scheduler.dto.schedule.*;
import org.example.scheduler.entity.Schedule;
import lombok.RequiredArgsConstructor;
import org.example.scheduler.entity.User;
import org.example.scheduler.repository.UserRepository;
import org.example.scheduler.util.ScheduleValidator;
import org.example.scheduler.util.exception.CustomException;
import org.example.scheduler.util.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.example.scheduler.repository.ScheduleRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final ScheduleValidator scheduleValidator;

    @Transactional
    public CreateScheduleResponse createSchedule(CreateScheduleRequest request, Long userId) {
        User findedUser = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Schedule schedule = new Schedule(request.getTitle(), request.getContent(), findedUser);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        ScheduleDTO savedScheduleDTO = new ScheduleDTO(savedSchedule);

        return new CreateScheduleResponse(savedScheduleDTO);
    }

    @Transactional(readOnly = true)
    public GetScheduleResponse getSchedule(Long scheduleId) {
        Schedule findedSchedule = scheduleValidator.checkExistScheduleById(scheduleId);

        ScheduleDTO findedScheduleDTO = new ScheduleDTO(findedSchedule);

        return new GetScheduleResponse(findedScheduleDTO);
    }

    @Transactional
    public UpdateScheduleResponse updateSchedule(Long userId, Long scheduleId, UpdateScheduleRequest request) {

        Schedule findedSchedule = scheduleValidator.checkExistScheduleById(scheduleId);

        if (!findedSchedule.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.INVALID_USER);
        }

        findedSchedule.modify(request.getTitle(), request.getContent());

        ScheduleDTO modifiedScheduleDTO = new ScheduleDTO(findedSchedule);

        return new UpdateScheduleResponse(modifiedScheduleDTO);
    }

    @Transactional
    public void deleteSchedule(Long userId, Long scheduleId) {
        Schedule findedSchedule = scheduleValidator.checkExistScheduleById(scheduleId);

        if (!findedSchedule.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.INVALID_USER);
        }
        scheduleRepository.deleteById(scheduleId);
    }

    @Transactional(readOnly = true)
    public Page<GetPagedScheduleResponse> getPagedSchedule(int pageNo) {
        Page<Schedule> pagedSchedules = scheduleRepository.findAll(Pageable.ofSize(10).withPage(pageNo));

        Page<ScheduleDTO> pagedScheduleDTOS = pagedSchedules.map(ScheduleDTO::new);

        return pagedScheduleDTOS.map(GetPagedScheduleResponse::new);

    }
}
