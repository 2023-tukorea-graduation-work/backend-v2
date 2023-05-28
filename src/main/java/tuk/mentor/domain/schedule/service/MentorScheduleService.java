package tuk.mentor.domain.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tuk.mentor.domain.schedule.dto.request.ScheduleRegisterRequest;
import tuk.mentor.domain.schedule.dto.response.ScheduleListResponse;
import tuk.mentor.domain.schedule.entity.MentorSchedule;
import tuk.mentor.domain.schedule.mapper.MentorScheduleMapper;
import tuk.mentor.domain.schedule.repository.MentorScheduleRepository;
import tuk.mentor.domain.user.mentor.repository.MentorRepository;
import tuk.mentor.util.DateUtil;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Qualifier("mentorScheduleService")
@RequiredArgsConstructor
public class MentorScheduleService{

    private final MentorScheduleRepository scheduleRepository;
    private final MentorRepository mentorRepository;
    private final MentorScheduleMapper mentorScheduleMapper;
    private final DateUtil dateUtil;

    /*
     * 일정 등록
     * */
    @Transactional
    public void registerSchedule(ScheduleRegisterRequest request) {
        scheduleRepository.save(MentorSchedule.builder()
                .mentor(mentorRepository.findById(request.getUserId()).orElseThrow(EntityNotFoundException::new))
                .content(request.getContent())
                .scheduleStartDatetime(dateUtil.convertStringToLocalDateTime(request.getScheduleStartDatetime()))
                .scheduleFinishDatetime(dateUtil.convertStringToLocalDateTime(request.getScheduleFinishDatetime()))
                .build());
    }

    public List<ScheduleListResponse> getScheduleList(Long userId) {
        return scheduleRepository.getScheduleList(userId)
                .stream().map(mentorScheduleMapper::toScheduleListDto).toList();
    }
}


