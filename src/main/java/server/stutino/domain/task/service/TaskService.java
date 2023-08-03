package server.stutino.domain.task.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import server.stutino.domain.task.dto.request.TaskRegisterRequest;
import server.stutino.domain.task.entity.Task;
import server.stutino.domain.task.repository.TaskRepository;
import server.stutino.util.CustomDateUtil;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskService {
    TaskRepository taskRepository;
    CustomDateUtil customDateUtil;
    
    /*
    * 과제 등록
    * */
    public void registerTask(TaskRegisterRequest request) {
        taskRepository.save(
                Task.builder()
                        .title(request.getTitle())
                        .content(request.getContent())
                        .startTaskDateTime(customDateUtil.convertStringToLocalDateTime(request.getStartTaskDateTime()))
                        .endTaskDateTime(customDateUtil.convertStringToLocalDateTime(request.getEndTaskDateTime()))
                        .build());
    }
}
