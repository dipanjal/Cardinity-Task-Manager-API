package rest.api.cardinity.taskmanager.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rest.api.cardinity.taskmanager.common.enums.ResponseCode;
import rest.api.cardinity.taskmanager.common.enums.TaskStatus;
import rest.api.cardinity.taskmanager.common.mappers.TaskObjectMapper;
import rest.api.cardinity.taskmanager.common.utils.ResponseUtil;
import rest.api.cardinity.taskmanager.models.entity.ProjectEntity;
import rest.api.cardinity.taskmanager.models.entity.TaskEntity;
import rest.api.cardinity.taskmanager.models.entity.UserDetailEntity;
import rest.api.cardinity.taskmanager.models.request.task.TaskCreationRequest;
import rest.api.cardinity.taskmanager.models.request.task.TaskUpdateRequest;
import rest.api.cardinity.taskmanager.models.response.Response;
import rest.api.cardinity.taskmanager.models.view.TaskModel;
import rest.api.cardinity.taskmanager.repository.ProjectRepository;
import rest.api.cardinity.taskmanager.repository.TaskRepository;
import rest.api.cardinity.taskmanager.repository.UserDetailRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@Service
@RequiredArgsConstructor
public class TaskService extends BaseService {

    private final TaskObjectMapper mapper;
    private final TaskRepository taskRepository;
    private final UserDetailRepository userDetailRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public Response<TaskModel> createNewTask(TaskCreationRequest request, UserDetailEntity currentUser){
        Response<UserDetailEntity> assignmentUserResponse = this.getAssignmentUserDetailResponse(request.getAssignedTo());
        if(ResponseCode.isNotSuccessful(assignmentUserResponse))
            return ResponseUtil.copyResponse(assignmentUserResponse);

        Response<ProjectEntity> projectResponse = this.getAssignmentProjectResponse(request.getProjectId());
        if(ResponseCode.isNotSuccessful(projectResponse))
            return ResponseUtil.copyResponse(projectResponse);

        TaskEntity entity = mapper.getNewTaskEntity(
                request, assignmentUserResponse.getItems(),
                currentUser, projectResponse.getItems()
        );
        taskRepository.create(entity);
        return ResponseUtil.createSuccessResponse(mapper.mapToTaskModel(entity));
    }

    @Transactional
    public Response<TaskModel> updateTask(TaskUpdateRequest request, UserDetailEntity currentUser){
        Optional<TaskEntity> entityOptional = taskRepository.getOpt(request.getTaskId());
        if(entityOptional.isEmpty())
            return ResponseUtil.createResponse(ResponseCode.RECORD_NOT_FOUND.getCode(), "Task Not Found");

        UserDetailEntity assignedToUser = entityOptional.get().getAssignedTo();
        if(this.isAssignmentUserUpdatable(entityOptional.get(), request.getAssignedTo())){
            Response<UserDetailEntity> userResponse = this.getAssignmentUserDetailResponse(request.getAssignedTo());
            if(ResponseCode.isNotSuccessful(userResponse))
                return ResponseUtil.copyResponse(userResponse);
            assignedToUser = userResponse.getItems();
        }

        ProjectEntity projectEntity = entityOptional.get().getProjectEntity();
        if(this.isProjectUpdatable(entityOptional.get(), request.getProjectId())){
            Response<ProjectEntity> projectResponse = this.getAssignmentProjectResponse(request.getProjectId());
            if(ResponseCode.isNotSuccessful(projectResponse))
                return ResponseUtil.copyResponse(projectResponse);
            projectEntity = projectResponse.getItems();
        }

        TaskEntity updatableTaskEntity = mapper.getUpdatableTaskEntity(
                entityOptional.get(), request, assignedToUser, currentUser, projectEntity
        );
        taskRepository.update(updatableTaskEntity);
        return ResponseUtil.createSuccessResponse(mapper.mapToTaskModel(updatableTaskEntity));
    }

    @Transactional(readOnly = true)
    public Response<List<TaskModel>> getAllTasksByProject(long projectId){
        List<TaskEntity> taskEntities = taskRepository.getByProjectId(projectId);
        if(CollectionUtils.isEmpty(taskEntities))
            return ResponseUtil.createResponse(ResponseCode.RECORD_NOT_FOUND.getCode(), "No Task Found for this Project");

        return ResponseUtil.createSuccessResponse(mapper.mapToTaskModel(taskEntities));
    }

    @Transactional(readOnly = true)
    public Response<List<TaskModel>> getTasksByStatus(int status){
        if(TaskStatus.isInvalidValidStatus(status))
            return ResponseUtil.createResponse(ResponseCode.BAD_REQUEST.getCode(), "Invalid Status");

        List<TaskEntity> taskEntities = taskRepository.getByStatus(status);
        if(CollectionUtils.isEmpty(taskEntities))
            return ResponseUtil.createResponse(ResponseCode.RECORD_NOT_FOUND.getCode(), "No Task Found for this Status");

        return ResponseUtil.createSuccessResponse(mapper.mapToTaskModel(taskEntities));
    }

    @Transactional(readOnly = true)
    public Response<List<TaskModel>> getAllExpiredTasks(){
        List<TaskEntity> taskEntities = taskRepository.getExpiredTasks();
        if(CollectionUtils.isEmpty(taskEntities))
            return ResponseUtil.createResponse(ResponseCode.RECORD_NOT_FOUND.getCode(), "No Expired Task Found");

        return ResponseUtil.createSuccessResponse(mapper.mapToTaskModel(taskEntities));
    }


    private Response<UserDetailEntity> getAssignmentUserDetailResponse(String userName){
        Optional<UserDetailEntity> entityOptional = userDetailRepository.getByUserNameOpt(userName);
        if(entityOptional.isEmpty())
            return ResponseUtil.createResponse(ResponseCode.RECORD_NOT_FOUND.getCode(), "Assignment User Not Found");

        return ResponseUtil.createSuccessResponse(entityOptional.get());
    }

    private Response<ProjectEntity> getAssignmentProjectResponse(long projectId){
        Optional<ProjectEntity> entityOptional = projectRepository.getOpt(projectId);
        if(entityOptional.isEmpty())
            return ResponseUtil.createResponse(ResponseCode.RECORD_NOT_FOUND.getCode(), "Assignment Project Not Found");

        return ResponseUtil.createSuccessResponse(entityOptional.get());
    }

    private boolean isAssignmentUserUpdatable(TaskEntity taskEntity, String assignedTo){
        if(taskEntity.getAssignedTo() == null)
            return StringUtils.isNotBlank(assignedTo);

        String alreadyAssignedTo = taskEntity.getAssignedTo().getUserName();
        return !StringUtils.equals(alreadyAssignedTo, assignedTo);
    }

    private boolean isProjectUpdatable(TaskEntity taskEntity, long projectId){
        if(projectId == 0)
            return false;

        return (taskEntity.getProjectEntity().getId() != projectId);
    }

}