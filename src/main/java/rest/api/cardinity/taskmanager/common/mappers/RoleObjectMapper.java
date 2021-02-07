package rest.api.cardinity.taskmanager.common.mappers;

import org.springframework.stereotype.Component;
import rest.api.cardinity.taskmanager.common.enums.SystemUserRole;
import rest.api.cardinity.taskmanager.models.entity.RoleEntity;

import java.util.Collection;
import java.util.Collections;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@Component
public class RoleObjectMapper {
    public RoleEntity getNewUserRole(SystemUserRole role) {
        return new RoleEntity(role.getCode(), role.getRole());
    }

    public Collection<RoleEntity> getNewUserRoleMapAsList(SystemUserRole systemUserRole){
        return Collections.singletonList(
                getNewUserRole(systemUserRole)
        );
    }
}
