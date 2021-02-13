package rest.api.cardinity.taskmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rest.api.cardinity.taskmanager.annotations.CardinityRestController;
import rest.api.cardinity.taskmanager.models.request.project.ProjectCreationRequest;
import rest.api.cardinity.taskmanager.models.request.project.ProjectUpdateRequest;
import rest.api.cardinity.taskmanager.models.response.Response;
import rest.api.cardinity.taskmanager.models.view.ProjectModel;
import rest.api.cardinity.taskmanager.service.ProjectService;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @author dipanjal
 * @since 2/5/2021
 */

@RestController
@RequestMapping("/project")
@CardinityRestController
@RequiredArgsConstructor
public class ProjectController extends BaseController {
    private final ProjectService projectService;

    @PostMapping("/create")
    public Response<ProjectModel> createNewProject(@RequestBody ProjectCreationRequest request){
        return projectService.createNewProject(request, super.getCurrentUser());
    }

    @PostMapping("/update")
    public Response<ProjectModel> updateProject(@RequestBody ProjectUpdateRequest request){
        return projectService.updateProject(request, super.getCurrentUser());
    }

    @GetMapping("/get")
    public Response<List<ProjectModel>> fetchUserProjects(){
        return projectService.getUserProjects(getCurrentUser());
    }

    @GetMapping("/get-all")
    public Response<List<ProjectModel>> fetchAllProjects(){
        return projectService.getAllProjects();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-by/{userName}")
    public Response<?> fetchProjectsByUserName(@PathVariable String userName){
        return projectService.getByUserName(userName);
    }

    @GetMapping("/delete/{id}")
    public Response<Long> delete(@PathVariable long id){
        return projectService.deleteProject(id);
    }

}

