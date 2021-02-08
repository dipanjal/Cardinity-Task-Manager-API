package rest.api.cardinity.taskmanager.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rest.api.cardinity.taskmanager.common.enums.ResponseCode;
import rest.api.cardinity.taskmanager.common.enums.SystemUserRole;
import rest.api.cardinity.taskmanager.common.mappers.DummyObjectMapper;
import rest.api.cardinity.taskmanager.common.mappers.UserDetailObjectMapper;
import rest.api.cardinity.taskmanager.common.utils.ResponseUtils;
import rest.api.cardinity.taskmanager.models.entity.RoleEntity;
import rest.api.cardinity.taskmanager.models.entity.UserDetailEntity;
import rest.api.cardinity.taskmanager.models.response.Response;
import rest.api.cardinity.taskmanager.models.view.CardinityUserDetailModel;
import rest.api.cardinity.taskmanager.repository.RoleRepository;
import rest.api.cardinity.taskmanager.repository.UserDetailRepository;

import java.util.*;

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
    private final RoleRepository roleRepository;

    @Value("${dummy.admin.username}")
    private String dummyAdminUserName;


    @Transactional
    public Response<List<CardinityUserDetailModel>> createDummies(){
        return this.createDummyUsers();
    }

    private Response<List<CardinityUserDetailModel>> createDummyUsers(){

        Collection<RoleEntity> roles = roleRepository.getAll();
        if(CollectionUtils.isEmpty(roles)){
            Response<Collection<RoleEntity>> roleResponse = this.createRoles();
            if(ResponseCode.isNotSuccessful(roleResponse))
                return ResponseUtils.copyResponse(roleResponse);
            roles = roleResponse.getItems();
        }

        List<UserDetailEntity> dummyUsers = userDetailRepository.getByUserNames(mapper.getDummyUserNames());
        if(CollectionUtils.isEmpty(dummyUsers)){
            RoleEntity adminRole = roles.stream().filter(role -> role.getName().equals(SystemUserRole.ADMIN.getRole())).findFirst().get();
            RoleEntity userRole = roles.stream().filter(role -> role.getName().equals(SystemUserRole.USER.getRole())).findFirst().get();

            List<UserDetailEntity> dummyUserEntities = new ArrayList<>();
            dummyUserEntities.add(mapper.getNewDummyAdminEntity(adminRole));
            dummyUserEntities.addAll(mapper.getNewDummyUserEntityList(userRole));
            userDetailRepository.create(dummyUserEntities);
            return ResponseUtils.createSuccessResponse(userMapper.mapToUserDetailModel(dummyUserEntities));
        }
        return ResponseUtils.createSuccessResponse(userMapper.mapToUserDetailModel(dummyUsers));
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

    private Response<Collection<RoleEntity>> createRoles(){
        Collection<RoleEntity> roleEntities = new HashSet<>();
        for(SystemUserRole role : SystemUserRole.values()){
            roleEntities.add(new RoleEntity(role.getCode(), role.getRole()));
        }
        roleRepository.create(roleEntities);
        return ResponseUtils.createSuccessResponse(roleEntities);
    }
}
