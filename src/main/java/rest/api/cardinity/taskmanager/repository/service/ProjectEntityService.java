package rest.api.cardinity.taskmanager.repository.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rest.api.cardinity.taskmanager.common.enums.ResponseCode;
import rest.api.cardinity.taskmanager.common.utils.ResponseUtils;
import rest.api.cardinity.taskmanager.models.entity.ProjectEntity;
import rest.api.cardinity.taskmanager.models.response.Response;
import rest.api.cardinity.taskmanager.repository.ProjectRepository;

import java.util.Optional;

/**
 * @author dipanjal
 * @since 2/8/2021
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ProjectEntityService {

    private final ProjectRepository projectRepository;

    public Response<ProjectEntity> getByIdResponse(long projectId){
        Optional<ProjectEntity> entityOptional = projectRepository.getOpt(projectId);
        if(entityOptional.isEmpty())
            return ResponseUtils.createResponse(ResponseCode.RECORD_NOT_FOUND.getCode(), "Project Not Found");
        return ResponseUtils.createSuccessResponse(entityOptional.get());
    }

    public Response delete(ProjectEntity entity){
        removeProjectAndTaskAssociation(entity);
        removeProjectAndUserAssociation(entity);
        projectRepository.delete(entity);
        return ResponseUtils.createSuccessResponse(null);
    }

    private void removeProjectAndTaskAssociation(ProjectEntity entity){
        entity.getTasks().forEach( task -> task.setProjectEntity(null));
        entity.getTasks().clear();
    }

    private void removeProjectAndUserAssociation(ProjectEntity entity){
        entity.getAssignedUsers().forEach(user -> user.getProjects().clear());
        entity.getAssignedUsers().clear();
    }
}
