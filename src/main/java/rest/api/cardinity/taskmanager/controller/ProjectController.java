package rest.api.cardinity.taskmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import rest.api.cardinity.taskmanager.models.request.project.ProjectCreationRequest;
import rest.api.cardinity.taskmanager.models.request.project.ProjectUpdateRequest;
import rest.api.cardinity.taskmanager.models.response.Response;
import rest.api.cardinity.taskmanager.models.view.ProjectModel;
import rest.api.cardinity.taskmanager.service.ProjectService;

import java.util.List;

/**
 * @author dipanjal
 * @since 2/5/2021
 */
@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController extends BaseController {
    private final ProjectService projectService;

    @PostMapping("/create")
    public Response<ProjectModel> createNewProject(@RequestBody ProjectCreationRequest request){
        return projectService.createNewProject(request, super.getCurrentDummyUser());
    }

    @PostMapping("/update")
    public Response<ProjectModel> updateProject(@RequestBody ProjectUpdateRequest request){
        return projectService.updateProject(request, super.getCurrentDummyUser());
    }

    @GetMapping("/get-all")
    public Response<List<ProjectModel>> fetchAllProjects(){
        return projectService.getAllProjects();
    }

    @GetMapping("/get-by/{userName}")
    public Response<List<ProjectModel>> fetchProjectsByUserName(@PathVariable String userName){
        return projectService.getByUserName(userName);
    }
}
