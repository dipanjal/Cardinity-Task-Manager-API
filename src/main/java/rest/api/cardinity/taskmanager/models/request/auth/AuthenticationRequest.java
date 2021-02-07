package rest.api.cardinity.taskmanager.models.request.auth;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author dipanjal
 * @since 2/7/2021
 */
@Getter
@Setter
public class AuthenticationRequest implements Serializable {
    private String username;
    private String password;
}
