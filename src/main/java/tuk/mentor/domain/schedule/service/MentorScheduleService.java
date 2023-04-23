package tuk.mentor.domain.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tuk.mentor.domain.mentor.repository.MentorRepository;
import tuk.mentor.domain.schedule.dto.request.ScheduleRegisterRequest;
import tuk.mentor.domain.schedule.dto.response.ScheduleListResponse;
import tuk.mentor.domain.schedule.entity.MentorSchedule;
import tuk.mentor.domain.schedule.mapper.MentorScheduleMapper;
import tuk.mentor.domain.schedule.repository.MentorScheduleRepository;
import tuk.mentor.global.login.LoginInfo;
import tuk.mentor.global.util.DateUtil;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Qualifier("mentorScheduleService")
@RequiredArgsConstructor
public class MentorScheduleService implements ScheduleService{

    private final MentorScheduleRepository scheduleRepository;
    private final MentorRepository mentorRepository;
    private final MentorScheduleMapper mentorScheduleMapper;
    private final DateUtil dateUtil;

    /*
     * 일정 등록
     * */
    @Transactional
    public void registerSchedule(ScheduleRegisterRequest request, LoginInfo loginInfo) {
        scheduleRepository.save(MentorSchedule.builder()
                .mentor(mentorRepository.findById(loginInfo.getUserID()).orElseThrow(EntityNotFoundException::new))
                .content(request.getContent())
                .scheduleStartDatetime(dateUtil.convertStringToLocalDateTime(request.getScheduleStartDatetime()))
                .scheduleFinishDatetime(dateUtil.convertStringToLocalDateTime(request.getScheduleFinishDatetime()))
                .build());
    }

    public List<ScheduleListResponse> getScheduleList(LoginInfo loginInfo) {
        return mentorScheduleMapper.toScheduleListDto(scheduleRepository.getScheduleList(loginInfo.getUserID()));
    }
}


