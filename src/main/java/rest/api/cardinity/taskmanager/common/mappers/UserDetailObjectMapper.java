package rest.api.cardinity.taskmanager.common.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import rest.api.cardinity.taskmanager.common.enums.Status;
import rest.api.cardinity.taskmanager.common.enums.SystemUserRole;
import rest.api.cardinity.taskmanager.models.entity.RoleEntity;
import rest.api.cardinity.taskmanager.models.entity.UserDetailEntity;
import rest.api.cardinity.taskmanager.models.view.CardinityUserDetailModel;

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
                this.getJoinedUserRoleValues(entity.getRoles()),
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
                this.getGrantedAuthorities(entity.getRoles())

        );
    }

    private String getJoinedUserRoleValues(Collection<RoleEntity> roles){
        return roles
                .stream()
                .map(role -> SystemUserRole.getValueByCode(role.getId()))
                .collect(Collectors.joining(","));
    }

/*    private List<String> getGrantedAuthorities(List<RoleEntity> roles){
        return roles
                .stream()
                .map(role -> SystemUserRole.getRoleByCode(role.getId()))
                .collect(Collectors.toList());
    }*/

    private List<GrantedAuthority> getGrantedAuthorities(Collection<RoleEntity> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
