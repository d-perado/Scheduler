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

    //일정 생성
    @Transactional
    public ScheduleResponse createSchedule(CreateScheduleRequest request, Long userId) {
        User foundUser = userRepository.findUserByIdOrThrow(userId);

        Schedule schedule = new Schedule(request.getTitle(), request.getContent(), foundUser);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponse(savedSchedule);
    }

    //일정 단건 조회
    @Transactional(readOnly = true)
    public ScheduleResponse getSchedule(Long scheduleId) {
        Schedule foundSchedule = scheduleRepository.findScheduleByIdOrThrow(scheduleId);

        return new ScheduleResponse(foundSchedule);
    }

    //일정 수정
    @Transactional
    public ScheduleResponse updateSchedule(Long userId, Long scheduleId, UpdateScheduleRequest request) {
        Schedule foundSchedule = scheduleRepository.findScheduleByIdOrThrow(scheduleId);

        validator.validateScheduleOwner(userId, foundSchedule);

        foundSchedule.modify(request.getTitle(), request.getContent());

        return new ScheduleResponse(foundSchedule);
    }

    //일정 삭제
    @Transactional
    public void deleteSchedule(Long userId, Long scheduleId) {
        Schedule foundSchedule = scheduleRepository.findScheduleByIdOrThrow(scheduleId);

        validator.validateScheduleOwner(userId, foundSchedule);

        commentRepository.deleteAllBySchedule_Id(scheduleId);
        scheduleRepository.deleteById(scheduleId);
    }

    //페이징된 전체 일정 조회
    @Transactional(readOnly = true)
    public Page<PagedScheduleResponse> getPagedSchedule(int pageNo, int pageSize) {
        //페이지 리퀘스트 생성
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "updatedAt"));
        //페이지 리퀘스트에 맞게 일정전체가져옴
        Page<Schedule> schedulePage = scheduleRepository.findAll(pageRequest);

        List<Schedule> pagedSchedules = schedulePage.getContent();

        if (pagedSchedules.isEmpty()) { //데이터 없으면 빈페이지
            return new PageImpl<>(Collections.emptyList(), pageRequest, schedulePage.getTotalElements());
        }

        //페이지에 포함된 모든 일정 id 뽑아옴
        List<Long> scheduleIds = pagedSchedules.stream()
                .map(Schedule::getId)
                .collect(Collectors.toList());
        //일정 리스트로 일정별 댓글 갯수 조회
        List<Tuple> commentCounts = commentRepository.countCommentsByScheduleIds(scheduleIds);
        //tuple > map 변환
        Map<Long, Long> commentCountMap = commentCounts.stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(0, Long.class),
                        tuple -> tuple.get(1, Long.class)
                ));
        //dto변환
        List<PagedScheduleResponse> response = pagedSchedules.stream()
                .map(scheduleItem -> {
                    long count = commentCountMap.getOrDefault(scheduleItem.getId(), 0L);//댓글갯수 0일때에도 값 넣어주기
                    return new PagedScheduleResponse(scheduleItem, Math.toIntExact(count));
                })
                .collect(Collectors.toList());

        return new PageImpl<>(response, pageRequest, schedulePage.getTotalElements());
    }

}
