package org.example.scheduler.service;

import org.example.scheduler.dto.CreateScheduleRequest;
import org.example.scheduler.dto.CreateScheduleResponse;
import org.example.scheduler.dto.GetScheduleResponse;
import org.example.scheduler.dto.ScheduleDTO;
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



    public GetScheduleResponse getSchedule(Long id){
        Schedule findedSchedule = scheduleRepository.findById(id).orElseThrow(
                ()-> new IllegalStateException("일정이 존재하지 않습니다.")
        );

        ScheduleDTO findedScheduleDTO = new ScheduleDTO(findedSchedule);

        return new GetScheduleResponse(findedScheduleDTO);
    }
}
