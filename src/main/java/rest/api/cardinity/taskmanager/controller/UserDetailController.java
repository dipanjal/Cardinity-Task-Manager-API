package rest.api.cardinity.taskmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all")
    public Response<List<CardinityUserDetailModel>> fetchAllUsers(){
        return cardinityUserDetailService.getAllUsers();
    }
}
