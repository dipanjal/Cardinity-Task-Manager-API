package rest.api.cardinity.taskmanager.common.mappers;

import org.springframework.stereotype.Component;
import rest.api.cardinity.taskmanager.common.enums.SystemUserRole;
import rest.api.cardinity.taskmanager.models.entity.UserDetailEntity;
import rest.api.cardinity.taskmanager.models.entity.UserRoleMapEntity;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@Component
public class UserRoleObjectMapper {
    public UserRoleMapEntity getNewUserRoleMap(UserDetailEntity entity, SystemUserRole admin) {
        UserRoleMapEntity roleMapEntity = new UserRoleMapEntity();
        roleMapEntity.setUserDetailEntity(entity);
        roleMapEntity.setRoleId(admin.getCode());
        roleMapEntity.setCreatedAt(new Date());
        return roleMapEntity;
    }

    public List<UserRoleMapEntity> getNewUserRoleMapAsList(UserDetailEntity entity, SystemUserRole systemUserRole){
        return Collections.singletonList(
                getNewUserRoleMap(entity, systemUserRole)
        );
    }
}
