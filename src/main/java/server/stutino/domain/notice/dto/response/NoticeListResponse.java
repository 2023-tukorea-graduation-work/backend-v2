package server.stutino.domain.notice.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
public class NoticeListResponse {
    private String title;
    private String content;
}
