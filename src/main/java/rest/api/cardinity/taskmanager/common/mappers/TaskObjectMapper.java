package rest.api.cardinity.taskmanager.common.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import rest.api.cardinity.taskmanager.common.enums.Status;
import rest.api.cardinity.taskmanager.common.enums.TaskStatus;
import rest.api.cardinity.taskmanager.common.utils.DateTimeUtils;
import rest.api.cardinity.taskmanager.models.entity.ProjectEntity;
import rest.api.cardinity.taskmanager.models.entity.TaskEntity;
import rest.api.cardinity.taskmanager.models.entity.UserDetailEntity;
import rest.api.cardinity.taskmanager.models.request.task.TaskCreationRequest;
import rest.api.cardinity.taskmanager.models.request.task.TaskUpdateRequest;
import rest.api.cardinity.taskmanager.models.view.TaskModel;

import java.util.Collection;
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

    public TaskEntity getNewTaskEntity(TaskCreationRequest request,
                                       UserDetailEntity assignedTo,
                                       UserDetailEntity createdByUser,
                                       ProjectEntity projectEntity){

        TaskEntity entity = new TaskEntity();

        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setExpireAt(DateTimeUtils.expireAtHour(request.getExpiryHour()));
        entity.setStatus(TaskStatus.getCodeByValue(request.getStatus()));
        entity.setProjectEntity(projectEntity);
        entity.setCreatedAt(new Date());
        entity.setUpdatedAt(new Date());
        entity.setCreatedById(createdByUser.getId());
        entity.setUpdatedById(createdByUser.getId());

        if(assignedTo != null)
            entity.setAssignedUserId(assignedTo.getId());

        return entity;
    }

    public TaskEntity getUpdatableTaskEntity(TaskEntity entity,
                                                TaskUpdateRequest request,
                                                UserDetailEntity assignedTo,
                                                UserDetailEntity updatedBy,
                                                ProjectEntity projectEntity){

        entity.setExpireAt(DateTimeUtils.expireAtHour(request.getExpiryHour()));
        entity.setDescription(request.getDescription());
        entity.setStatus(TaskStatus.getCodeByValue(request.getStatus()));
        entity.setUpdatedById(updatedBy.getId());
        entity.setUpdatedAt(new Date());

        if(assignedTo != null)
            entity.setAssignedUserId(assignedTo.getId());
        if(projectEntity != null)
            entity.setProjectEntity(projectEntity);

        return entity;
    }

    public TaskModel mapToTaskModel(TaskEntity entity){
        return new TaskModel (
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                TaskStatus.getValueByCode(entity.getStatus()),
                DateTimeUtils.formatDate(entity.getCreatedAt()),
                DateTimeUtils.formatDate(entity.getUpdatedAt()),
                DateTimeUtils.formatDate(entity.getExpireAt()),
                entity.getCreatedById(),
                entity.getUpdatedById(),
                entity.getAssignedUserId(),
                projectObjectMapper.mapToProjectModel(entity.getProjectEntity())
        );
    }

    public List<TaskModel> mapToTaskModel(Collection<TaskEntity> entityList){
        return entityList
                .stream()
                .map(this::mapToTaskModel)
                .collect(Collectors.toList());
    }
}
