package rest.api.cardinity.taskmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rest.api.cardinity.taskmanager.models.request.task.TaskCreationRequest;
import rest.api.cardinity.taskmanager.models.request.task.TaskUpdateRequest;
import rest.api.cardinity.taskmanager.models.response.Response;
import rest.api.cardinity.taskmanager.models.view.TaskModel;
import rest.api.cardinity.taskmanager.service.TaskService;

import java.util.List;

/**
 * @author dipanjal
 * @since 2/5/2021
 */
@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController extends BaseController {
    private final TaskService taskService;

    @PostMapping("/create")
    public Response<TaskModel> createNewProject(@RequestBody TaskCreationRequest request){
        return taskService.createNewTask(request, super.getCurrentUser());
    }

    @PostMapping("/update")
    public Response<TaskModel> updateProject(@RequestBody TaskUpdateRequest request){
        return taskService.updateTask(request, super.getCurrentUser());
    }

    @GetMapping("/get")
    public Response<List<TaskModel>> fetchUserTasks(){
        return taskService.getUserTasks(super.getCurrentUser());
    }

    @GetMapping("/get-all")
    public Response<List<TaskModel>> fetchAllTasks(){
        return taskService.getAllTasks();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all/by-user/{userName}")
    public Response<List<TaskModel>> fetchAllTasksByProject(@PathVariable String userName){
        return taskService.getUserTasks(userName);
    }

    @GetMapping("/get-all/expired")
    public Response<List<TaskModel>> fetchAllExpiredTasks(){
        return taskService.getAllExpiredTasks();
    }

    @GetMapping("/get-all/by-project/{projectId}")
    public Response<List<TaskModel>> fetchAllTasksByProject(@PathVariable long projectId){
        return taskService.getAllTasksByProject(projectId);
    }

    @GetMapping("/get-by/status/{taskStatus}")
    public Response<List<TaskModel>> fetchProjectsByUserName(@PathVariable String taskStatus){
        return taskService.getTasksByStatus(taskStatus);
    }
}
