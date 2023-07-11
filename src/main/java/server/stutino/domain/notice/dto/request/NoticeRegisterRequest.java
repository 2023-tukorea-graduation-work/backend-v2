package server.stutino.domain.notice.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class NoticeRegisterRequest {
    @NotNull
    private Long programId;
    @NotNull
    private String title;
    @NotNull
    private String content;
}
