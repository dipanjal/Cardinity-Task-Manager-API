package rest.api.cardinity.taskmanager.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rest.api.cardinity.taskmanager.common.enums.ResponseCode;
import rest.api.cardinity.taskmanager.common.mappers.ProjectObjectMapper;
import rest.api.cardinity.taskmanager.common.utils.ResponseUtils;
import rest.api.cardinity.taskmanager.models.entity.ProjectEntity;
import rest.api.cardinity.taskmanager.models.entity.UserDetailEntity;
import rest.api.cardinity.taskmanager.models.request.project.BaseProjectRequest;
import rest.api.cardinity.taskmanager.models.request.project.ProjectCreationRequest;
import rest.api.cardinity.taskmanager.models.request.project.ProjectUpdateRequest;
import rest.api.cardinity.taskmanager.models.response.Response;
import rest.api.cardinity.taskmanager.models.view.ProjectModel;
import rest.api.cardinity.taskmanager.repository.ProjectRepository;
import rest.api.cardinity.taskmanager.repository.service.UserDetailEntityService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@Service
@RequiredArgsConstructor
public class ProjectService extends BaseService {
    private final ProjectObjectMapper mapper;
    private final ProjectRepository projectRepository;
    private final UserDetailEntityService userEntityService;

    @Transactional
    public Response<ProjectModel> createNewProject(ProjectCreationRequest request, UserDetailEntity currentUser){

        Response<String> modelValidationResponse = this.validateRequestModel(request);
        if(ResponseCode.isNotSuccessful(modelValidationResponse))
            return ResponseUtils.copyResponse(modelValidationResponse);

        Response<UserDetailEntity> assignedUserResponse = userEntityService.getByUserName(request.getAssignedTo());
        if(ResponseCode.isNotSuccessful(assignedUserResponse))
            return ResponseUtils.copyResponse(assignedUserResponse);

        ProjectEntity entity = mapper.getNewProjectEntity(request, assignedUserResponse.getItems(), currentUser);
        projectRepository.create(entity);
        return ResponseUtils.createSuccessResponse(mapper.mapToProjectModel(entity));
    }

    @Transactional
    public Response<ProjectModel> updateProject(ProjectUpdateRequest request, UserDetailEntity currentUser){
        Response<String> modelValidationResponse = this.validateRequestModel(request);
        if(ResponseCode.isNotSuccessful(modelValidationResponse))
            return ResponseUtils.copyResponse(modelValidationResponse);

        Optional<ProjectEntity> existingProjectOpt = projectRepository.getOpt(request.getProjectId());
        if(existingProjectOpt.isEmpty())
            return ResponseUtils.createResponse(ResponseCode.RECORD_NOT_FOUND.getCode(), "Invalid project id, Record not found");

        Response<UserDetailEntity> assignedUserResponse = userEntityService.getByUserName(request.getAssignedTo());
        if(ResponseCode.isNotSuccessful(assignedUserResponse))
            return ResponseUtils.copyResponse(assignedUserResponse);
        Set<UserDetailEntity> assignedUsers = Collections.singleton(assignedUserResponse.getItems());

        ProjectEntity entity = mapper.getUpdatableProjectEntity(existingProjectOpt.get(), request, assignedUsers, currentUser);
        projectRepository.update(entity);
        return ResponseUtils.createSuccessResponse(mapper.mapToProjectModel(entity));
    }

    @Transactional(readOnly = true)
    public Response<List<ProjectModel>> getAllProjects(){
        List<ProjectEntity> projectEntities = projectRepository.getAll();
        if(CollectionUtils.isEmpty(projectEntities))
            return ResponseUtils.createResponse(ResponseCode.RECORD_NOT_FOUND.getCode(), "Record Not Found");

        return ResponseUtils.createSuccessResponse(mapper.mapToProjectModel(projectEntities));
    }

    @Transactional(readOnly = true)
    public Response<List<ProjectModel>> getByUserName(String userName){
        List<ProjectEntity> projectEntities = projectRepository.getByUserName(userName);
        if(CollectionUtils.isEmpty(projectEntities))
            return ResponseUtils.createResponse(ResponseCode.RECORD_NOT_FOUND.getCode(), "Record Not Found");

        return ResponseUtils.createSuccessResponse(mapper.mapToProjectModel(projectEntities));
    }

    @Transactional(readOnly = true)
    public Response<ProjectModel> getById(long id){
        Optional<ProjectEntity> entityOptional = projectRepository.getOpt(id);
        if(entityOptional.isEmpty())
            return ResponseUtils.createResponse(ResponseCode.RECORD_NOT_FOUND.getCode(), "Project Not Found");

        return ResponseUtils.createSuccessResponse(mapper.mapToProjectModel(entityOptional.get()));
    }

    protected Response<String> validateRequestModel(BaseProjectRequest request){
        List<String> violationMessages = request.validate(request);
        if(CollectionUtils.isNotEmpty(violationMessages))
            return ResponseUtils.createResponse(ResponseCode.BAD_REQUEST.getCode(), joinResponseMessage(violationMessages));
        return ResponseUtils.createSuccessResponse(null);
    }
}
