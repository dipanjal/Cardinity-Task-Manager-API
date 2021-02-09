package rest.api.cardinity.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import rest.api.cardinity.taskmanager.common.enums.ResponseCode;
import rest.api.cardinity.taskmanager.common.utils.JWTUtils;
import rest.api.cardinity.taskmanager.models.entity.UserDetailEntity;
import rest.api.cardinity.taskmanager.models.response.Response;
import rest.api.cardinity.taskmanager.repository.service.UserDetailEntityService;
import rest.api.cardinity.taskmanager.service.CardinityUserDetailService;
import rest.api.cardinity.taskmanager.service.DummyService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dipanjal
 * @since 2/5/2021
 */

public abstract class BaseController {
    private DummyService dummyService;
    private HttpServletRequest request;
    private UserDetailEntityService userDetailEntityService;

    @Value("${auth.header.name}")
    private String authHeaderName;

    @Autowired
    public void inject(DummyService dummyService,
                       HttpServletRequest request,
                       UserDetailEntityService userDetailEntityService){
        this.dummyService = dummyService;
        this.request = request;
        this.userDetailEntityService = userDetailEntityService;
    }

    protected UserDetailEntity getCurrentDummyUser(){
        return dummyService.getDummyAdminEntity().getItems();
    }

    protected UserDetailEntity getCurrentUser(){
        String token = JWTUtils.trimToken(request.getHeader(authHeaderName));
        String userName = JWTUtils.extractUserName(token);
        Response<UserDetailEntity> response = userDetailEntityService.getByUserName(userName);
        if(ResponseCode.isNotSuccessful(response))
            return null;
        return response.getItems();
    }
}
