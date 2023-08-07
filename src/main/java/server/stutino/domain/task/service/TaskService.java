package server.stutino.domain.task.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import server.stutino.domain.program.repository.ProgramRepository;
import server.stutino.domain.task.dto.request.TaskRegisterRequest;
import server.stutino.domain.task.dto.response.TaskListResponse;
import server.stutino.domain.task.entity.Task;
import server.stutino.domain.task.entity.TaskFile;
import server.stutino.domain.task.repository.TaskFileRepository;
import server.stutino.domain.task.repository.TaskRepository;
import server.stutino.util.CustomDateUtil;
import server.stutino.util.s3.manager.S3Manager;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskService {
    TaskRepository taskRepository;
    TaskFileRepository taskFileRepository;
    ProgramRepository programRepository;
    S3Manager s3Manager;
    CustomDateUtil customDateUtil;

    /*
    * 과제 등록
    * */
    @Transactional
    public void registerTask(TaskRegisterRequest request, List<MultipartFile> files) {
        // [1] 과제 인스턴스 생성
        Task task = Task.builder()
                .program(programRepository.findById(request.getProgramId()).orElseThrow(EntityNotFoundException::new))
                .title(request.getTitle())
                .content(request.getContent())
                .startTaskDateTime(customDateUtil.convertStringToLocalDateTime(request.getStartTaskDateTime()))
                .endTaskDateTime(customDateUtil.convertStringToLocalDateTime(request.getEndTaskDateTime()))
                .build();
        taskRepository.save(task);

        // [2] 과제 내 파일 등록
        List<TaskFile> taskFiles = new ArrayList<>();

        for(MultipartFile file : files) {
            try {
                String filePath = s3Manager.upload(file, "material");

                taskFiles.add(TaskFile.builder()
                        .task(task)
                        .fileName(file.getOriginalFilename())
                        .filePath(filePath)
                        .build());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        taskFileRepository.saveAll(taskFiles);

    }

    /*
    * 과제 조회
    * */
    public List<TaskListResponse> findAllTask(Long programId) {
        return  taskRepository.findAllTaskByProgram(programId).stream().map(task ->
                new TaskListResponse(
                        task.getId(),
                        task.getTitle(),
                        task.getContent(),
                        task.getStartTaskDateTime(),
                        task.getEndTaskDateTime())
                ).toList();
    }
}
