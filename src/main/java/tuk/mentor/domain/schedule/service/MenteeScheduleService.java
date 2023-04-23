package tuk.mentor.domain.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tuk.mentor.domain.mentee.repository.MenteeRepository;
import tuk.mentor.domain.schedule.dto.request.ScheduleRegisterRequest;
import tuk.mentor.domain.schedule.dto.response.ScheduleListResponse;
import tuk.mentor.domain.schedule.entity.MenteeSchedule;
import tuk.mentor.domain.schedule.mapper.MenteeScheduleMapper;
import tuk.mentor.domain.schedule.repository.MenteeScheduleRepository;
import tuk.mentor.global.login.LoginInfo;
import tuk.mentor.global.util.DateUtil;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Qualifier("menteeScheduleService")
@RequiredArgsConstructor
public class MenteeScheduleService implements ScheduleService{

    private final MenteeScheduleRepository scheduleRepository;
    private final MenteeRepository menteeRepository;
    private final MenteeScheduleMapper menteeScheduleMapper;
    private final DateUtil dateUtil;

    /*
     * 일정 등록
     * */
    @Transactional
    public void registerSchedule(ScheduleRegisterRequest request, LoginInfo loginInfo) {
        scheduleRepository.save(MenteeSchedule.builder()
                .mentee(menteeRepository.findById(loginInfo.getUserID()).orElseThrow(EntityNotFoundException::new))
                .content(request.getContent())
                .scheduleStartDatetime(dateUtil.convertStringToLocalDateTime(request.getScheduleStartDatetime()))
                .scheduleStartDatetime(dateUtil.convertStringToLocalDateTime(request.getScheduleFinishDatetime()))
                .build());
    }

    public List<ScheduleListResponse> getScheduleList(LoginInfo loginInfo) {
        return menteeScheduleMapper.toScheduleListDto(scheduleRepository.getScheduleList(loginInfo.getUserID()));
    }
}


