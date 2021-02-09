package rest.api.cardinity.taskmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rest.api.cardinity.taskmanager.models.request.auth.AuthenticationRequest;
import rest.api.cardinity.taskmanager.models.response.Response;
import rest.api.cardinity.taskmanager.models.view.AuthenticationModel;
import rest.api.cardinity.taskmanager.service.auth.AuthenticationServiceFactory;

/**
 * @author dipanjal
 * @since 2/7/2021
 */
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationServiceFactory authServiceFactory;

    @PostMapping("/authenticate")
    public Response<AuthenticationModel> authenticate(@RequestBody AuthenticationRequest request) {
        return authServiceFactory.getBasicAuthService().authenticate(request);
    }
}
