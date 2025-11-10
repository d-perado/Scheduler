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
    public CreateScheduleResponse createSchedule(CreateScheduleRequest request,Long userId) {
        User findedUser = userRepository.findById(userId).orElseThrow(
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
    public UpdateScheduleResponse updateSchedule(Long userId, Long scheduleId, UpdateScheduleRequest request) {

        Schedule findedSchedule = scheduleValidator.checkExistScheduleById(scheduleId);

        if(!findedSchedule.getId().equals(userId)){
            throw new IllegalArgumentException("작성자 본인이 아닙니다.");
        }

        findedSchedule.modify(request.getTitle(), request.getContent());

        ScheduleDTO modifiedScheduleDTO = new ScheduleDTO(findedSchedule);

        return new UpdateScheduleResponse(modifiedScheduleDTO);
    }

    @Transactional
    public void deleteSchedule(Long userId, Long scheduleId) {
        Schedule findedSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(()-> new IllegalStateException("존재하지 않는 일정입니다."));

        if(!findedSchedule.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("작성자가 적은 글이 아닙니다.");
        }
            scheduleRepository.deleteById(scheduleId);
    }
}
