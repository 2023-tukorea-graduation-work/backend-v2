package tuk.mentor.domain.schedule.service;

import org.springframework.stereotype.Service;
import tuk.mentor.domain.schedule.dto.request.ScheduleRegisterRequest;
import tuk.mentor.domain.schedule.dto.response.ScheduleListResponse;
import tuk.mentor.global.login.LoginInfo;

import java.util.List;

@Service
public interface ScheduleService {
    void registerSchedule(ScheduleRegisterRequest request, LoginInfo loginInfo) throws Exception;
    List<ScheduleListResponse> getScheduleList(LoginInfo loginInfo);
}
