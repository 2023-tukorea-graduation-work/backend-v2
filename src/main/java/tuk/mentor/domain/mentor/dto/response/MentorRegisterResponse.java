package tuk.mentor.domain.mentor.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tuk.mentor.domain.mentor.entity.Role;

@Getter // @Getter가 붙어야 Json 타입으로 객체를 반환할 수 있다.
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MentorRegisterResponse {
    private Long id;
    private Role role;
}
