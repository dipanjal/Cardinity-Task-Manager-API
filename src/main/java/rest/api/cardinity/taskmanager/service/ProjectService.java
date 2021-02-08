package rest.api.cardinity.taskmanager.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rest.api.cardinity.taskmanager.common.enums.ResponseCode;
import rest.api.cardinity.taskmanager.common.enums.TaskStatus;
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
import rest.api.cardinity.taskmanager.repository.service.ProjectEntityService;
import rest.api.cardinity.taskmanager.repository.service.UserDetailEntityService;

import java.util.*;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@Service
@RequiredArgsConstructor
public class ProjectService extends BaseService {
    private final ProjectObjectMapper mapper;
    private final ProjectRepository projectRepository;
    private final ProjectEntityService projectEntityService;
    private final UserDetailEntityService userEntityService;

    @Transactional
    public Response<ProjectModel> createNewProject(ProjectCreationRequest request, UserDetailEntity currentUser){

        Response<String> modelValidationResponse = this.validateRequestModel(request);
        if(ResponseCode.isNotSuccessful(modelValidationResponse))
            return ResponseUtils.copyResponse(modelValidationResponse);

        UserDetailEntity assignedUser = null;
        if(StringUtils.isNotBlank(request.getAssignedTo())){
            Response<UserDetailEntity> response = userEntityService.getByUserName(request.getAssignedTo());
            if(ResponseCode.isNotSuccessful(response))
                return ResponseUtils.copyResponse(response);
            assignedUser = response.getItems();
        }
        Set<UserDetailEntity> assignedUsers = Collections.singleton(assignedUser);

        ProjectEntity entity = mapper.getNewProjectEntity(request, assignedUsers, currentUser);
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
        if(TaskStatus.isClosed(existingProjectOpt.get().getStatus()))
            return ResponseUtils.createResponse(ResponseCode.BAD_REQUEST.getCode(), "Sorry! Cannot edit closed projects");

        UserDetailEntity assignedUser = null;
        if(StringUtils.isNotBlank(request.getAssignedTo())){
            Response<UserDetailEntity> response = userEntityService.getByUserName(request.getAssignedTo());
            if(ResponseCode.isNotSuccessful(response))
                return ResponseUtils.copyResponse(response);
            assignedUser = response.getItems();
        }
        Set<UserDetailEntity> assignedUsers = Collections.singleton(assignedUser);

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
        Response<UserDetailEntity> userEntityResponse = userEntityService.getByUserName(userName);
        if(ResponseCode.isNotSuccessful(userEntityResponse))
            return ResponseUtils.copyResponse(userEntityResponse);

        Set<ProjectEntity> userProjectEntityList = userEntityResponse.getItems().getProjects();
        if(CollectionUtils.isEmpty(userProjectEntityList))
            return ResponseUtils.createResponse(ResponseCode.RECORD_NOT_FOUND.getCode(), "No project is assigned to this user");

        return ResponseUtils.createSuccessResponse(mapper.mapToProjectModel(userProjectEntityList));
    }

    @Transactional(readOnly = true)
    public Response<List<ProjectModel>> getUserProjects(UserDetailEntity currentUser){
        Collection<ProjectEntity> projectEntities = currentUser.getProjects();
        if(CollectionUtils.isEmpty(projectEntities))
            return ResponseUtils.createResponse(ResponseCode.RECORD_NOT_FOUND.getCode(), "You don't have any assigned Project");
        List<ProjectModel> projectModels = mapper.mapToProjectModel(projectEntities);
        return ResponseUtils.createSuccessResponse(projectModels);
    }

    @Transactional(readOnly = true)
    public Response<ProjectModel> getById(long id){
        Response<ProjectEntity> response = projectEntityService.getByIdResponse(id);
        if(ResponseCode.isNotSuccessful(response))
            return ResponseUtils.copyResponse(response);
        return ResponseUtils.createSuccessResponse(mapper.mapToProjectModel(response.getItems()));
    }

    @Transactional
    public Response<Long> deleteProject(long projectId){
        Response<ProjectEntity> response = projectEntityService.getByIdResponse(projectId);
        if(ResponseCode.isNotSuccessful(response))
            return ResponseUtils.copyResponse(response);
        projectEntityService.delete(response.getItems());
        return ResponseUtils.createSuccessResponse(projectId);
    }

    private Response<String> validateRequestModel(BaseProjectRequest request){
        List<String> violationMessages = new ArrayList<>();
        if(request instanceof ProjectCreationRequest)
            violationMessages = ((ProjectCreationRequest)request).validate();
        else if (request instanceof ProjectUpdateRequest)
            violationMessages = ((ProjectUpdateRequest)request).validate();

        if(CollectionUtils.isNotEmpty(violationMessages))
            return ResponseUtils.createResponse(ResponseCode.BAD_REQUEST.getCode(), joinResponseMessage(violationMessages));
        return ResponseUtils.createSuccessResponse(null);
    }
}
