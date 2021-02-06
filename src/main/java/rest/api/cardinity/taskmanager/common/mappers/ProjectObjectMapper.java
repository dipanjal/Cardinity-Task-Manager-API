package rest.api.cardinity.taskmanager.common.mappers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import rest.api.cardinity.taskmanager.common.enums.Status;
import rest.api.cardinity.taskmanager.common.utils.CStringUtils;
import rest.api.cardinity.taskmanager.common.utils.DateTimeUtils;
import rest.api.cardinity.taskmanager.models.entity.ProjectEntity;
import rest.api.cardinity.taskmanager.models.entity.UserDetailEntity;
import rest.api.cardinity.taskmanager.models.request.project.ProjectCreationRequest;
import rest.api.cardinity.taskmanager.models.request.project.ProjectUpdateRequest;
import rest.api.cardinity.taskmanager.models.view.ProjectModel;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@Component
public class ProjectObjectMapper {
    
    /** @// TODO: 2/6/2021 pass current logged In user account */
    public ProjectEntity getNewProjectEntity(ProjectCreationRequest request, UserDetailEntity createdByUser){
        ProjectEntity entity = new ProjectEntity();

        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setCreatedAt(new Date());
        entity.setUpdatedAt(new Date());
        entity.setCreatedBy(createdByUser);
        entity.setUpdatedBy(createdByUser);

        return entity;
    }

    /** @// TODO: 2/6/2021 Need to check: A different object with the same identifier value was already associated with the session   */
    public ProjectEntity getUpdatableProjectEntity(ProjectEntity entity, ProjectUpdateRequest request,
                                                   UserDetailEntity updatedByUser){
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setUpdatedAt(new Date());
//        entity.setUpdatedBy(updatedByUser);

        return entity;
    }

    public ProjectModel mapToProjectModel(ProjectEntity entity){
        return new ProjectModel(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                Status.getValueByCode(entity.getStatus()),
                DateTimeUtils.formatDate(entity.getCreatedAt()),
                DateTimeUtils.formatDate(entity.getUpdatedAt()),
                entity.getCreatedBy().getName()
        );
    }

    public List<ProjectModel> mapToProjectModel(List<ProjectEntity> entityList){
        return entityList
                .stream()
                .map(this::mapToProjectModel)
                .collect(Collectors.toList());
    }
}
