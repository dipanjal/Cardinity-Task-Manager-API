package rest.api.cardinity.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import rest.api.cardinity.taskmanager.models.entity.UserDetailEntity;
import rest.api.cardinity.taskmanager.service.DummyService;

/**
 * @author dipanjal
 * @since 2/5/2021
 */

public abstract class BaseController {
    private DummyService dummyService;

    @Autowired
    public void inject(Environment environment, DummyService dummyService){
        this.dummyService = dummyService;
    }

    protected UserDetailEntity getCurrentDummyUser(){
        return dummyService.getDummyAdminEntity().getItems();
    }
}
