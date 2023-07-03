package server.stutino.domain.schedule.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.stutino.domain.member.repository.MemberRepository;
import server.stutino.domain.schedule.dto.request.ScheduleRegisterRequest;
import server.stutino.domain.schedule.dto.response.ScheduleListResponse;
import server.stutino.domain.schedule.entity.Schedule;
import server.stutino.domain.schedule.mapper.ScheduleMapper;
import server.stutino.domain.schedule.repository.ScheduleRepository;
import server.stutino.util.CustomDateUtil;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScheduleService {
    
    ScheduleRepository scheduleRepository;
    MemberRepository memberRepository;
    ScheduleMapper scheduleMapper;
    CustomDateUtil customDateUtil;

    /*
     * 일정 등록
     * */
    @Transactional
    public void registerSchedule(ScheduleRegisterRequest request) {
        scheduleRepository.save(Schedule.builder()
                .member(memberRepository.findById(request.getMemberId()).orElseThrow(EntityNotFoundException::new))
                .content(request.getContent())
                .scheduleStartDatetime(customDateUtil.convertStringToLocalDateTime(request.getScheduleStartDatetime()))
                .scheduleFinishDatetime(customDateUtil.convertStringToLocalDateTime(request.getScheduleFinishDatetime()))
                .build());
    }

    /*
    * 일정 조회
    * */
    public List<ScheduleListResponse> getScheduleList(Long memberId) {
        return scheduleRepository.getScheduleList(memberId)
                .stream().map(scheduleMapper::toScheduleListDto).toList();
    }
}


