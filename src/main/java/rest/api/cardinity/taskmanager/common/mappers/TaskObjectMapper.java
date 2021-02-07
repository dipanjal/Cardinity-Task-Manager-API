package rest.api.cardinity.taskmanager.common.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rest.api.cardinity.taskmanager.common.enums.Status;
import rest.api.cardinity.taskmanager.common.utils.DateTimeUtils;
import rest.api.cardinity.taskmanager.models.entity.ProjectEntity;
import rest.api.cardinity.taskmanager.models.entity.TaskEntity;
import rest.api.cardinity.taskmanager.models.entity.UserDetailEntity;
import rest.api.cardinity.taskmanager.models.request.task.TaskCreationRequest;
import rest.api.cardinity.taskmanager.models.request.task.TaskUpdateRequest;
import rest.api.cardinity.taskmanager.models.view.TaskModel;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@Component
@RequiredArgsConstructor
public class TaskObjectMapper {

    private final ProjectObjectMapper projectObjectMapper;


    /** @// TODO: 2/6/2021 Need to check UserDetailEntity: A different object with the same identifier value was already associated with the session */
    public TaskEntity getNewTaskEntity(TaskCreationRequest request,
                                       UserDetailEntity assignedTo,
                                       UserDetailEntity createdByUser,
                                       ProjectEntity projectEntity){

        TaskEntity entity = new TaskEntity();

        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setExpireAt(DateTimeUtils.expireAtHour(request.getExpiryHour()));
        entity.setStatus(request.getStatus());
        entity.setAssignedTo(assignedTo);
        entity.setProjectEntity(projectEntity);
        entity.setCreatedAt(new Date());
        entity.setUpdatedAt(new Date());
        entity.setCreatedBy(createdByUser);
        entity.setUpdatedBy(createdByUser);

        return entity;
    }

    /** @// TODO: 2/6/2021 Need to check: A different object with the same identifier value was already associated with the session   */
    public TaskEntity getUpdatableTaskEntity(TaskEntity entity,
                                                TaskUpdateRequest request,
                                                UserDetailEntity assignedTo,
                                                UserDetailEntity updatedBy,
                                                ProjectEntity projectEntity){

        if(projectEntity != null)
            entity.setProjectEntity(projectEntity);

        entity.setExpireAt(DateTimeUtils.expireAtHour(request.getExpiryHour()));
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setAssignedTo(assignedTo);
        entity.setUpdatedAt(new Date());

        return entity;
    }

    public TaskModel mapToTaskModel(TaskEntity entity){
        return new TaskModel (
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                Status.getValueByCode(entity.getStatus()),
                DateTimeUtils.formatDate(entity.getCreatedAt()),
                DateTimeUtils.formatDate(entity.getUpdatedAt()),
                entity.getCreatedBy().getName(),
                entity.getAssignedTo().getName(),
                projectObjectMapper.mapToProjectModel(entity.getProjectEntity())
        );
    }

    public List<TaskModel> mapToTaskModel(List<TaskEntity> entityList){
        return entityList
                .stream()
                .map(this::mapToTaskModel)
                .collect(Collectors.toList());
    }
}
