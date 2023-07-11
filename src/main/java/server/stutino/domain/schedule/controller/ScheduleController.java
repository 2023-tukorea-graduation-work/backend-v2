package server.stutino.domain.schedule.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.stutino.domain.schedule.dto.request.ScheduleRegisterRequest;
import server.stutino.domain.schedule.dto.response.ScheduleListResponse;
import server.stutino.domain.schedule.service.ScheduleService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScheduleController {
    ScheduleService scheduleService;

    /*
    * 일정 등록
    * */
    @PostMapping
    public ResponseEntity<Void> registerSchedule(@Valid @RequestBody ScheduleRegisterRequest scheduleRegisterRequest) {
        scheduleService.registerSchedule(scheduleRegisterRequest);
        return ResponseEntity.ok().build();
    }

    /*
    * 일정 조회
    * */
    @GetMapping("/{memberId}")
    public ResponseEntity<List<ScheduleListResponse>> getScheduleList(@PathVariable("memberId") Long memberId) {
        List<ScheduleListResponse> response = scheduleService.getScheduleList(memberId);
        return ResponseEntity.ok().body(response);
    }
}
