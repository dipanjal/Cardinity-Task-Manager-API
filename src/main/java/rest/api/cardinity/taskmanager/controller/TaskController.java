package rest.api.cardinity.taskmanager.controller;

import lombok.RequiredArgsConstructor;
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

    @GetMapping("/get-all/expired")
    public Response<List<TaskModel>> fetchAllExpiredTaks(){
        return taskService.getAllExpiredTasks();
    }

    @GetMapping("/get-all/by-project/{projectId}")
    public Response<List<TaskModel>> fetchAllTasksByProject(@PathVariable long projectId){
        return taskService.getAllTasksByProject(projectId);
    }

    @GetMapping("/get-by/status/{taskStatus}")
    public Response<List<TaskModel>> fetchProjectsByUserName(@PathVariable int taskStatus){
        return taskService.getTasksByStatus(taskStatus);
    }
}
