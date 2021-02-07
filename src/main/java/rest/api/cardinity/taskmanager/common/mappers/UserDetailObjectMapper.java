package rest.api.cardinity.taskmanager.common.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import rest.api.cardinity.taskmanager.common.enums.Status;
import rest.api.cardinity.taskmanager.common.enums.SystemUserRole;
import rest.api.cardinity.taskmanager.models.entity.UserDetailEntity;
import rest.api.cardinity.taskmanager.models.entity.UserRoleMapEntity;
import rest.api.cardinity.taskmanager.models.view.CardinityUserDetailModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@Component
@RequiredArgsConstructor
public class UserDetailObjectMapper {

    /** @// TODO: 2/6/2021 Need to set role from db */
    public CardinityUserDetailModel mapToUserDetailModel(UserDetailEntity entity){
        return new CardinityUserDetailModel(
                entity.getUserName(),
                entity.getName(),
                entity.getEmail(),
                this.getJoinedUserRoleValues(entity.getUserRoleMaps()),
                entity.getDesignation(),
                Status.getValueByCode(entity.getStatus())
        );
    }

    public List<CardinityUserDetailModel> mapToUserDetailModel(Collection<UserDetailEntity> entityList){
        return entityList
                .stream()
                .map(this::mapToUserDetailModel)
                .collect(Collectors.toList());
    }

    public User mapToUserModel(UserDetailEntity entity){
        return new User(
                entity.getUserName(),
                entity.getPassword(),
                new ArrayList<>()
        );
    }

    private String getJoinedUserRoleValues(List<UserRoleMapEntity> roleMapEntities){
        return roleMapEntities
                .stream()
                .map(roleMap -> SystemUserRole.getValueByCode(roleMap.getRoleId()))
                .collect(Collectors.joining(","));
    }
}
