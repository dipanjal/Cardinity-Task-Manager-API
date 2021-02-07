package rest.api.cardinity.taskmanager.service.auth;

import org.springframework.security.core.userdetails.UserDetails;
import rest.api.cardinity.taskmanager.models.request.auth.AuthenticationRequest;
import rest.api.cardinity.taskmanager.models.response.Response;
import rest.api.cardinity.taskmanager.models.view.AuthenticationModel;

/**
 * @author dipanjal
 * @since 2/7/2021
 */
public interface IAuthenticationService {
    Response<AuthenticationModel> authenticate(AuthenticationRequest request);
    Response<UserDetails> validateToken(String token);
}
