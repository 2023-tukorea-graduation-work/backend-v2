package server.stutino.domain.notice.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class NoticeRegisterRequest {
    private Long programId;
    private String title;
    private String content;
}
