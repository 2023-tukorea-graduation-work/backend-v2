package server.stutino.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MyPageResponse {
    private String name;
    private Integer age;
    private String email;
    private String imgUrl;
    private List<MyProgramResponse> programList;
}
