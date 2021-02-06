package rest.api.cardinity.taskmanager.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rest.api.cardinity.taskmanager.common.enums.ResponseCode;
import rest.api.cardinity.taskmanager.common.mappers.ProjectObjectMapper;
import rest.api.cardinity.taskmanager.common.utils.ResponseUtil;
import rest.api.cardinity.taskmanager.models.entity.ProjectEntity;
import rest.api.cardinity.taskmanager.models.entity.UserDetailEntity;
import rest.api.cardinity.taskmanager.models.request.project.ProjectCreationRequest;
import rest.api.cardinity.taskmanager.models.request.project.ProjectUpdateRequest;
import rest.api.cardinity.taskmanager.models.response.Response;
import rest.api.cardinity.taskmanager.models.view.ProjectModel;
import rest.api.cardinity.taskmanager.repository.ProjectRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@Service
@RequiredArgsConstructor
public class ProjectService extends BaseService {
    private final DummyService dummyService;
    private final ProjectObjectMapper mapper;
    private final ProjectRepository projectRepository;

    @Transactional
    public Response<ProjectModel> createNewProject(ProjectCreationRequest request, UserDetailEntity currentUser){
        ProjectEntity entity = mapper.getNewProjectEntity(request, currentUser);
        projectRepository.create(entity);
        return ResponseUtil.createSuccessResponse(mapper.mapToProjectModel(entity));
    }

    @Transactional
    public Response<ProjectModel> updateProject(ProjectUpdateRequest request, UserDetailEntity currentUser){
        Optional<ProjectEntity> existingProjectOpt = projectRepository.getOpt(request.getProjectId());
        if(existingProjectOpt.isEmpty())
            return ResponseUtil.createResponse(ResponseCode.RECORD_NOT_FOUND.getCode(), "Invalid project id, Record not found");

        ProjectEntity entity = mapper.getUpdatableProjectEntity(existingProjectOpt.get(), request, currentUser);
        projectRepository.update(entity);
        return ResponseUtil.createSuccessResponse(mapper.mapToProjectModel(entity));
    }

    @Transactional
    public Response<List<ProjectModel>> getAllProjects(){
        List<ProjectEntity> projectEntities = projectRepository.getAll();
        if(CollectionUtils.isEmpty(projectEntities))
            return ResponseUtil.createResponse(ResponseCode.RECORD_NOT_FOUND.getCode(), "Record Not Found");

        return ResponseUtil.createSuccessResponse(mapper.mapToProjectModel(projectEntities));
    }

    @Transactional
    public Response<List<ProjectModel>> getByUserName(String userName){
        List<ProjectEntity> projectEntities = projectRepository.getByUserName(userName);
        if(CollectionUtils.isEmpty(projectEntities))
            return ResponseUtil.createResponse(ResponseCode.RECORD_NOT_FOUND.getCode(), "Record Not Found");

        return ResponseUtil.createSuccessResponse(mapper.mapToProjectModel(projectEntities));
    }
}
