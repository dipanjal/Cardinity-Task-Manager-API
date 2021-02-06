package rest.api.cardinity.taskmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rest.api.cardinity.taskmanager.models.response.Response;
import rest.api.cardinity.taskmanager.models.view.UserDetailModel;
import rest.api.cardinity.taskmanager.service.UserDetailService;

import java.util.List;

/**
 * @author dipanjal
 * @since 2/6/2021
 */

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserDetailController {

    private final UserDetailService userDetailService;

    @GetMapping("/get-all")
    public Response<List<UserDetailModel>> fetchAllUsers(){
        return userDetailService.getAllUsers();
    }
}
