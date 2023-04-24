package tuk.mentor.domain.notice.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class NoticeListResponse {
    private String title;
    private String content;
}
