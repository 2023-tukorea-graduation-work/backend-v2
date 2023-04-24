package tuk.mentor.domain.notice.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class NoticeRegisterRequest {
    private Long programId;
    private String title;
    private String content;
}
