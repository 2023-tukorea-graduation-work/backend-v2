package tuk.mentor.domain.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tuk.mentor.domain.schedule.dto.request.ScheduleRegisterRequest;
import tuk.mentor.domain.schedule.dto.response.ScheduleListResponse;
import tuk.mentor.domain.schedule.service.MenteeScheduleService;
import tuk.mentor.domain.schedule.service.MentorScheduleService;
import tuk.mentor.domain.schedule.service.ScheduleService;
import tuk.mentor.global.login.LoginInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService mentorScheduleService;
    private final ScheduleService menteeScheduleService;

    @PostMapping
    public ResponseEntity<Void> registerSchedule(@RequestBody ScheduleRegisterRequest scheduleRegisterRequest,
                                                 HttpServletRequest httpServletRequest) {

        LoginInfo loginInfo = (LoginInfo)httpServletRequest.getSession().getAttribute("loginInfo");

        switch (loginInfo.getRole()) {
            case MENTOR -> {
                ((MentorScheduleService) mentorScheduleService).registerSchedule(scheduleRegisterRequest, loginInfo);
            }
            case MENTEE -> {
                ((MenteeScheduleService) menteeScheduleService).registerSchedule(scheduleRegisterRequest, loginInfo);
            }
            default -> throw new RuntimeException("Unknown role: " + loginInfo.getRole());
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ScheduleListResponse>> getScheduleList(HttpServletRequest httpServletRequest) {

        LoginInfo loginInfo = (LoginInfo)httpServletRequest.getSession().getAttribute("loginInfo");

        List<ScheduleListResponse> response;

        switch (loginInfo.getRole()) {
            case MENTOR -> {
                response = ((MentorScheduleService) mentorScheduleService).getScheduleList(loginInfo);
            }
            case MENTEE -> {
                response = ((MenteeScheduleService) menteeScheduleService).getScheduleList(loginInfo);
            }
            default -> throw new RuntimeException("Unknown role: " + loginInfo.getRole());
        }
        return ResponseEntity.ok().body(response);
    }
}
