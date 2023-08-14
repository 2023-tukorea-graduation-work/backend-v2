package server.stutino.domain.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import server.stutino.domain.member.entity.Lesson;
import server.stutino.domain.member.entity.Major;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MemberListResponse {
    private Long memberId;
    private LocalDateTime createdAt;
    private String email;
    private String name;
    private Integer age;
    private String college;
    private Major major;
    private Lesson lesson;
    private String filepath;
    private Boolean isPassed;
}
