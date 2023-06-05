package tuk.mentor.domain.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tuk.mentor.domain.schedule.dto.request.ScheduleRegisterRequest;
import tuk.mentor.domain.schedule.dto.response.ScheduleListResponse;
import tuk.mentor.domain.schedule.service.MenteeScheduleService;
import tuk.mentor.domain.schedule.service.MentorScheduleService;

import java.util.List;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final MentorScheduleService mentorScheduleService;
    private final MenteeScheduleService menteeScheduleService;

    /*
    * 프로그램 별 멘토 일정 등록
    * */
    @PostMapping("/mentor")
    public ResponseEntity<Void> registerMentorSchedule(@RequestBody ScheduleRegisterRequest scheduleRegisterRequest) {
        mentorScheduleService.registerSchedule(scheduleRegisterRequest);
        return ResponseEntity.ok().build();
    }

    /*
    * 프로그램 별 멘티 일정 등록
    * */
    @PostMapping("/mentee")
    public ResponseEntity<Void> registerMenteeSchedule(@RequestBody ScheduleRegisterRequest scheduleRegisterRequest) {
        menteeScheduleService.registerSchedule(scheduleRegisterRequest);
        return ResponseEntity.ok().build();
    }

    /*
    * 프로그램 별 멘토 일정 조회
    * */
    @GetMapping("/mentor/{userId}")
    public ResponseEntity<List<ScheduleListResponse>> getMentorScheduleList(@PathVariable("userId") Long userId) {
        List<ScheduleListResponse> response = mentorScheduleService.getScheduleList(userId);
        return ResponseEntity.ok().body(response);
    }

    /*
    * 프로그램 별 멘티 일정 조회
    * */
    @GetMapping("/mentee/{userId}")
    public ResponseEntity<List<ScheduleListResponse>> getMenteeScheduleList(@PathVariable("userId") Long userId) {
        List<ScheduleListResponse> response = mentorScheduleService.getScheduleList(userId);
        return ResponseEntity.ok().body(response);
    }
}
