package rest.api.cardinity.taskmanager.common.mappers;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import rest.api.cardinity.taskmanager.common.enums.Status;
import rest.api.cardinity.taskmanager.common.utils.DateTimeUtils;
import rest.api.cardinity.taskmanager.models.entity.ProjectEntity;
import rest.api.cardinity.taskmanager.models.entity.UserDetailEntity;
import rest.api.cardinity.taskmanager.models.request.project.ProjectCreationRequest;
import rest.api.cardinity.taskmanager.models.request.project.ProjectUpdateRequest;
import rest.api.cardinity.taskmanager.models.view.ProjectModel;
import rest.api.cardinity.taskmanager.models.view.UserDetailModel;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@Component
@RequiredArgsConstructor
public class ProjectObjectMapper {
    private final UserDetailObjectMapper userDetailObjectMapper;
    
    /** @// TODO: 2/6/2021 pass current logged In user account */
    public ProjectEntity getNewProjectEntity(ProjectCreationRequest request,
                                             UserDetailEntity assignedUser,
                                             UserDetailEntity createdByUser){
        ProjectEntity entity = new ProjectEntity();

        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setCreatedAt(new Date());
        entity.setUpdatedAt(new Date());
        entity.setCreatedBy(createdByUser);
        entity.setUpdatedBy(createdByUser);
        this.setUserProjectAssociation(entity, assignedUser);
        return entity;
    }

    private void setUserProjectAssociation(ProjectEntity entity, UserDetailEntity assignedUser){
        Set<UserDetailEntity> assignedUsers = CollectionUtils.isEmpty(entity.getUsers())
                ? new HashSet<>() : entity.getUsers();
        assignedUsers.add(assignedUser);

        Set<ProjectEntity> assignedProjects = CollectionUtils.isEmpty(assignedUser.getProjects())
                ? new HashSet<>() : assignedUser.getProjects();
        assignedProjects.add(entity);

        entity.setUsers(assignedUsers);
        assignedUser.setProjects(assignedProjects);
    }

    public ProjectEntity getUpdatableProjectEntity(ProjectEntity entity, ProjectUpdateRequest request,
                                                   UserDetailEntity updatedByUser){
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setUpdatedAt(new Date());
        entity.setUpdatedBy(updatedByUser);

        return entity;
    }

    public ProjectModel mapToProjectModel(ProjectEntity entity){
        List<UserDetailModel> assignedUsers = userDetailObjectMapper.mapToUserDetailModel(entity.getUsers());

        return new ProjectModel(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                Status.getValueByCode(entity.getStatus()),
                DateTimeUtils.formatDate(entity.getCreatedAt()),
                DateTimeUtils.formatDate(entity.getUpdatedAt()),
                entity.getCreatedBy().getName(),
                assignedUsers
        );
    }

    public List<ProjectModel> mapToProjectModel(List<ProjectEntity> entityList){
        return entityList
                .stream()
                .map(this::mapToProjectModel)
                .collect(Collectors.toList());
    }
}
