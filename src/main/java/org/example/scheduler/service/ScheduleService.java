package org.example.scheduler.service;

import org.example.scheduler.dto.schedule.*;
import org.example.scheduler.entity.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.example.scheduler.repository.ScheduleRepository;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public CreateScheduleResponse createSchedule(CreateScheduleRequest request){
        Schedule schedule = new Schedule(request.getTitle(), request.getContent(), request.getWriter());

        Schedule savedSchedule = scheduleRepository.save(schedule);

        ScheduleDTO savedScheduleDTO = new ScheduleDTO(savedSchedule);

        return new CreateScheduleResponse(savedScheduleDTO);
    }

    public GetScheduleResponse getSchedule(Long scheduleId){
        Schedule findedSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(()-> new IllegalStateException("일정이 존재하지 않습니다."));

        ScheduleDTO findedScheduleDTO = new ScheduleDTO(findedSchedule);

        return new GetScheduleResponse(findedScheduleDTO);
    }

    public UpdateScheduleResponse updateSchedule(Long scheduleId, UpdateScheduleRequest request){
        Schedule findedSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(()-> new IllegalStateException(("일정이 존재하지 않습니다.")));

        findedSchedule.modify(request.getTitle(),request.getContent());

        ScheduleDTO modifiedScheduleDTO = new ScheduleDTO(findedSchedule);

        return new UpdateScheduleResponse(modifiedScheduleDTO);
    }
}
