package rest.api.cardinity.taskmanager.repository.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rest.api.cardinity.taskmanager.common.enums.ResponseCode;
import rest.api.cardinity.taskmanager.common.utils.ResponseUtils;
import rest.api.cardinity.taskmanager.models.entity.UserDetailEntity;
import rest.api.cardinity.taskmanager.models.response.Response;
import rest.api.cardinity.taskmanager.repository.UserDetailRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author dipanjal
 * @since 2/7/2021
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailEntityService {
    private final UserDetailRepository repository;

    public Response<UserDetailEntity> getByUserName(String userName){
        Optional<UserDetailEntity> opt = repository.getByUserNameOpt(userName);
        if(opt.isEmpty())
            return ResponseUtils.createResponse(ResponseCode.RECORD_NOT_FOUND.getCode(), "User not found");
        return ResponseUtils.createSuccessResponse(opt.get());
    }
}
