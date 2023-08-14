package server.stutino.domain.admin.mapper;

import org.springframework.stereotype.Component;
import server.stutino.domain.admin.dto.response.MemberListResponse;
import server.stutino.domain.member.entity.Member;

@Component
public class AdminMapper {
    public MemberListResponse toDto(Member member) {
        return new MemberListResponse(
                member.getId(),
                member.getCreatedAt(),
                member.getEmail(),
                member.getName(),
                member.getAge(),
                member.getInstitution(),
                member.getMajor(),
                member.getLesson(),
                member.getCertificateUrl(),
                member.getIsPassed()
        );
    }
}
