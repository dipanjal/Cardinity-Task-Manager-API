package rest.api.cardinity.taskmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rest.api.cardinity.taskmanager.models.response.Response;
import rest.api.cardinity.taskmanager.models.view.CardinityUserDetailModel;
import rest.api.cardinity.taskmanager.service.CardinityUserDetailService;

import java.util.List;

/**
 * @author dipanjal
 * @since 2/6/2021
 */

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserDetailController {

    private final CardinityUserDetailService cardinityUserDetailService;

    @GetMapping(value = "/get-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<CardinityUserDetailModel>> fetchAllUsers(){
        return cardinityUserDetailService.getAllUsers();
    }
}
