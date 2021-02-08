package rest.api.cardinity.taskmanager.service.auth;

import org.springframework.stereotype.Component;
import rest.api.cardinity.taskmanager.common.utils.ApplicationContextHolder;

/**
 * @author dipanjal
 * @since 2/7/2021
 */
@Component
public class AuthenticationServiceFactory {

    public IAuthenticationService getBasicAuthService(){
        return ApplicationContextHolder.getContext().getBean(BasicAuthServiceImpl.class);
    }


}
