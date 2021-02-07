package rest.api.cardinity.taskmanager.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rest.api.cardinity.taskmanager.common.enums.ResponseCode;
import rest.api.cardinity.taskmanager.common.mappers.DummyObjectMapper;
import rest.api.cardinity.taskmanager.common.mappers.UserDetailObjectMapper;
import rest.api.cardinity.taskmanager.common.utils.ResponseUtils;
import rest.api.cardinity.taskmanager.models.entity.UserDetailEntity;
import rest.api.cardinity.taskmanager.models.response.Response;
import rest.api.cardinity.taskmanager.models.view.CardinityUserDetailModel;
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
    public Response<List<CardinityUserDetailModel>> createDummies(){
        return this.createDummyUsers();
    }

    private Response<List<CardinityUserDetailModel>> createDummyUsers(){
        Response<List<CardinityUserDetailModel>> existingDummiesResponse = this.getDummyUsers();
        if(ResponseCode.isSuccessful(existingDummiesResponse))
            return existingDummiesResponse;

        List<UserDetailEntity> dummyUserEntities = new ArrayList<>();
        dummyUserEntities.add(mapper.getNewDummyAdminEntity());
        dummyUserEntities.add(mapper.getNewDummyUserEntity());
        userDetailRepository.create(dummyUserEntities);
        return ResponseUtils.createSuccessResponse(userMapper.mapToUserDetailModel(dummyUserEntities));
    }

    @Transactional
    public Response<List<CardinityUserDetailModel>> getDummyUsers(){
        List<UserDetailEntity> dummyUsers = userDetailRepository.getByUserNames(mapper.getDummyUserNames());
        if(CollectionUtils.isEmpty(dummyUsers))
            return ResponseUtils.createResponse(ResponseCode.RECORD_NOT_FOUND.getCode(), "No Dummy User Found");

        List<CardinityUserDetailModel> dummyUserModels = userMapper.mapToUserDetailModel(dummyUsers);
        return ResponseUtils.createSuccessResponse(dummyUserModels);
    }

    @Transactional
    public Response<UserDetailEntity> getDummyAdminEntity(){
        Optional<UserDetailEntity> dummyAdminOpt = userDetailRepository.getByUserNameOpt(dummyAdminUserName);
        if(dummyAdminOpt.isEmpty())
            return ResponseUtils.createResponse(ResponseCode.RECORD_NOT_FOUND.getCode(), "User Not Found");

        return ResponseUtils.createSuccessResponse(dummyAdminOpt.get());
    }

/*    @Transactional
    public Response<List<UserRoleMapEntity>> getUserRoles(){
        List<UserRoleMapEntity> userRoleMapEntities = userRoleMapRepository.getAll();
        return ResponseUtils.createSuccessResponse(userRoleMapEntities);
    }*/
}
