package rest.api.cardinity.taskmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rest.api.cardinity.taskmanager.models.response.Response;
import rest.api.cardinity.taskmanager.models.view.CardinityUserDetailModel;
import rest.api.cardinity.taskmanager.service.DummyService;

import java.util.List;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@RestController
@RequestMapping("/dummy")
@RequiredArgsConstructor
public class DummyController {
    private final DummyService dummyService;

    @GetMapping("/initiate")
    public Response<List<CardinityUserDetailModel>> createDummyUsers(){
        return dummyService.createDummies();
    }

    @GetMapping("/users")
    public Response<List<CardinityUserDetailModel>> fetchDummyUsers(){
        return dummyService.getDummyUsers();
    }
}
