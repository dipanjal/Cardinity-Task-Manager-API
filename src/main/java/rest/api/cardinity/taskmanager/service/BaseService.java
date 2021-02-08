package rest.api.cardinity.taskmanager.service;

/**
 * @author dipanjal
 * @since 2/6/2021
 */

import rest.api.cardinity.taskmanager.common.utils.ResponseUtils;

import java.util.List;

public abstract class BaseService {

    protected String joinResponseMessage(List<String> responseMessages){
        return ResponseUtils.joinResponseMessage(responseMessages);
    }
}
