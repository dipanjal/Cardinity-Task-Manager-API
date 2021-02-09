package rest.api.cardinity.taskmanager.common.mappers;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import rest.api.cardinity.taskmanager.common.enums.TaskStatus;
import rest.api.cardinity.taskmanager.common.utils.DBDataUtils;
import rest.api.cardinity.taskmanager.common.utils.DateTimeUtils;
import rest.api.cardinity.taskmanager.models.entity.ProjectEntity;
import rest.api.cardinity.taskmanager.models.entity.UserDetailEntity;
import rest.api.cardinity.taskmanager.models.request.project.ProjectCreationRequest;
import rest.api.cardinity.taskmanager.models.request.project.ProjectUpdateRequest;
import rest.api.cardinity.taskmanager.models.view.CardinityUserDetailModel;
import rest.api.cardinity.taskmanager.models.view.ProjectModel;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@Component
@RequiredArgsConstructor
public class ProjectObjectMapper {
    private final UserDetailObjectMapper userDetailObjectMapper;

    public ProjectEntity getNewProjectEntity(ProjectCreationRequest request,
                                             Set<UserDetailEntity> assignedUsers,
                                             UserDetailEntity createdByUser){
        ProjectEntity entity = new ProjectEntity();

        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(TaskStatus.getCodeByValue(request.getStatus()));
        entity.setCreatedAt(new Date());
        entity.setUpdatedAt(new Date());
        entity.setCreatedById(createdByUser.getId());
        entity.setUpdatedById(createdByUser.getId());
        if(CollectionUtils.isNotEmpty(assignedUsers))
            setUserProjectAssociation(entity, assignedUsers);
        return entity;
    }

    public ProjectEntity getUpdatableProjectEntity(ProjectEntity entity,
                                                   ProjectUpdateRequest request,
                                                   Set<UserDetailEntity> assignedUsers,
                                                   UserDetailEntity updatedByUser){
        entity.setDescription(request.getDescription());
        entity.setStatus(TaskStatus.getCodeByValue(request.getStatus()));
        entity.setUpdatedAt(new Date());
        entity.setUpdatedById(updatedByUser.getId());
        if(CollectionUtils.isNotEmpty(assignedUsers))
            setUserProjectAssociation(entity, assignedUsers);

        return entity;
    }

    private void setUserProjectAssociation(ProjectEntity entity, Set<UserDetailEntity> assignedUsers){
        assignedUsers.forEach(user -> user.getProjects().add(entity));
        entity.setAssignedUsers(assignedUsers);
    }

    public ProjectModel mapToProjectModel(ProjectEntity entity){

        List<CardinityUserDetailModel> assignedUserModels = DBDataUtils.isEmpty(entity.getAssignedUsers())
                ? new ArrayList<>() : userDetailObjectMapper.mapToUserDetailModel(entity.getAssignedUsers());

        return new ProjectModel(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                TaskStatus.getValueByCode(entity.getStatus()),
                DateTimeUtils.formatDate(entity.getCreatedAt()),
                DateTimeUtils.formatDate(entity.getUpdatedAt()),
                entity.getCreatedById(),
                entity.getUpdatedById()
//                assignedUserModels
        );
    }

    public List<ProjectModel> mapToProjectModel(Collection<ProjectEntity> entityList){
        return entityList
                .stream()
                .map(this::mapToProjectModel)
                .collect(Collectors.toList());
    }
}
