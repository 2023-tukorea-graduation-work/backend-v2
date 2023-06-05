package tuk.mentor.domain.attend.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tuk.mentor.domain.attend.dto.response.AttendListByProgramResponse;
import tuk.mentor.domain.attend.service.AttendService;

@RestController
@RequestMapping("/attend")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttendController {
    AttendService attendService;

    /*
    * 프로그램 별 출석 인원 목록 조회
    * */
/*    @GetMapping("/program/{programId}/")
    public ResponseEntity<AttendListByProgramResponse> getAttendanceListByProgram(@PathVariable("programId") Long programId) {
        AttendListByProgramResponse response = attendService.getAttendanceListByProgram(programId);
        return ResponseEntity.ok().body(response);
    }*/
}