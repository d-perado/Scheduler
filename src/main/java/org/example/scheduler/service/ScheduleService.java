package org.example.scheduler.service;

import org.example.scheduler.dto.schedule.*;
import org.example.scheduler.entity.Schedule;
import lombok.RequiredArgsConstructor;
import org.example.scheduler.entity.User;
import org.example.scheduler.repository.UserRepository;
import org.example.scheduler.util.ScheduleValidator;
import org.springframework.stereotype.Service;
import org.example.scheduler.repository.ScheduleRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final ScheduleValidator scheduleValidator;

    @Transactional
    public CreateScheduleResponse createSchedule(CreateScheduleRequest request) {
        User findedUser = userRepository.findById(request.getUserId()).orElseThrow(
                ()-> new IllegalStateException("존재하지 않는 유저입니다."));

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
    public UpdateScheduleResponse updateSchedule(Long scheduleId, UpdateScheduleRequest request) {

        Schedule findedSchedule = scheduleValidator.checkExistScheduleById(scheduleId);

        findedSchedule.modify(request.getTitle(), request.getContent());

        ScheduleDTO modifiedScheduleDTO = new ScheduleDTO(findedSchedule);

        return new UpdateScheduleResponse(modifiedScheduleDTO);
    }

    @Transactional
    public void deleteSchedule(Long scheduleId) {
        boolean existence = scheduleRepository.existsById(scheduleId);

        if (existence) {
            scheduleRepository.deleteById(scheduleId);
        }
    }
}
