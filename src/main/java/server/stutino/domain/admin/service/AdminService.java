package server.stutino.domain.admin.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import server.stutino.domain.admin.dto.request.PassedMentorRequest;
import server.stutino.domain.admin.dto.response.MemberListResponse;
import server.stutino.domain.admin.mapper.AdminMapper;
import server.stutino.domain.member.entity.Member;
import server.stutino.domain.member.repository.MemberRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminService {
    MemberRepository memberRepository;
    AdminMapper adminMapper;

    /*
     * 관리자 페이지 회원 목록 조회
     * */
    public List<MemberListResponse> findAllMember() {
        List<Member> members = memberRepository.findAll();
        return members.stream().map(member -> {
            if(member.getRoles().contains("ROLE_MENTOR"))
                return adminMapper.toDto(member);
            return null;
        }).toList();
    }

    /*
    * 멘토 등록 승인/거부
    * */
    public void registerPassedMentor(PassedMentorRequest request) {
        Member member = memberRepository.findById(request.getMentorId()).orElseThrow(EntityNotFoundException::new);
        member.setIsPassed(request.getIsPassed());
        memberRepository.save(member);
    }
}