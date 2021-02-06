package rest.api.cardinity.taskmanager.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rest.api.cardinity.taskmanager.common.enums.ResponseCode;
import rest.api.cardinity.taskmanager.common.mappers.UserDetailObjectMapper;
import rest.api.cardinity.taskmanager.common.utils.ResponseUtil;
import rest.api.cardinity.taskmanager.models.entity.UserDetailEntity;
import rest.api.cardinity.taskmanager.models.response.Response;
import rest.api.cardinity.taskmanager.models.view.UserDetailModel;
import rest.api.cardinity.taskmanager.repository.UserDetailRepository;

import java.util.List;
import java.util.ResourceBundle;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@Service
@RequiredArgsConstructor
public class UserDetailService extends BaseService {

    private final UserDetailRepository userDetailRepository;
    protected final UserDetailObjectMapper mapper;

    @Transactional(readOnly = true)
    public Response<List<UserDetailModel>> getAllUsers() {
        List<UserDetailEntity> userDetailEntities = userDetailRepository.getAll();
        if(CollectionUtils.isEmpty(userDetailEntities))
            return ResponseUtil.createResponse(ResponseCode.RECORD_NOT_FOUND.getCode(), "No user found");
        return ResponseUtil.createSuccessResponse(mapper.mapToUserDetailModel(userDetailEntities));
    }
}
