package rest.api.cardinity.taskmanager.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rest.api.cardinity.taskmanager.common.enums.ResponseCode;
import rest.api.cardinity.taskmanager.common.mappers.DummyObjectMapper;
import rest.api.cardinity.taskmanager.common.mappers.UserDetailObjectMapper;
import rest.api.cardinity.taskmanager.common.utils.ResponseUtil;
import rest.api.cardinity.taskmanager.models.entity.UserDetailEntity;
import rest.api.cardinity.taskmanager.models.response.Response;
import rest.api.cardinity.taskmanager.models.view.UserDetailModel;
import rest.api.cardinity.taskmanager.repository.UserDetailRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author dipanjal
 * @since 2/6/2021
 */

@Service
@RequiredArgsConstructor
public class DummyService {

    private final DummyObjectMapper mapper;
    private final UserDetailObjectMapper userMapper;
    private final UserDetailRepository userDetailRepository;

    @Value("${dummy.admin.username}")
    private String dummyAdminUserName;


    @Transactional
    public Response<List<UserDetailModel>> createDummies(){
        return this.createDummyUsers();
    }

    private Response<List<UserDetailModel>> createDummyUsers(){
        Response<List<UserDetailModel>> existingDummiesResponse = this.getDummyUsers();
        if(ResponseCode.isSuccessful(existingDummiesResponse))
            return existingDummiesResponse;

        List<UserDetailEntity> dummyUserEntities = new ArrayList<>();
        dummyUserEntities.add(mapper.getNewDummyAdminEntity());
        dummyUserEntities.add(mapper.getNewDummyUserEntity());
        userDetailRepository.create(dummyUserEntities);
        return ResponseUtil.createSuccessResponse(userMapper.mapToUserDetailModel(dummyUserEntities));
    }

    @Transactional
    public Response<List<UserDetailModel>> getDummyUsers(){
        List<UserDetailEntity> dummyUsers = userDetailRepository.getByUserNames(mapper.getDummyUserNames());
        if(CollectionUtils.isEmpty(dummyUsers))
            return ResponseUtil.createResponse(ResponseCode.RECORD_NOT_FOUND.getCode(), "No Dummy User Found");

        List<UserDetailModel> dummyUserModels = userMapper.mapToUserDetailModel(dummyUsers);
        return ResponseUtil.createSuccessResponse(dummyUserModels);
    }

    @Transactional
    public Response<UserDetailEntity> getDummyAdminEntity(){
        Optional<UserDetailEntity> dummyAdminOpt = userDetailRepository.getByUserNameOpt(dummyAdminUserName);
        if(dummyAdminOpt.isEmpty())
            return ResponseUtil.createResponse(ResponseCode.RECORD_NOT_FOUND.getCode(), "User Not Found");

        return ResponseUtil.createSuccessResponse(dummyAdminOpt.get());
    }

/*    @Transactional
    public Response<List<UserRoleMapEntity>> getUserRoles(){
        List<UserRoleMapEntity> userRoleMapEntities = userRoleMapRepository.getAll();
        return ResponseUtil.createSuccessResponse(userRoleMapEntities);
    }*/
}
