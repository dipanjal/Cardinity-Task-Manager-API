package rest.api.cardinity.taskmanager.common.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import rest.api.cardinity.taskmanager.common.enums.Status;
import rest.api.cardinity.taskmanager.common.enums.SystemUserRole;
import rest.api.cardinity.taskmanager.models.entity.UserDetailEntity;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@Component
@RequiredArgsConstructor
public class DummyObjectMapper {
    private final Environment env;
    private final UserRoleObjectMapper userRoleMapper;

    public String[] getDummyUserNames(){
        return new String[] {
                env.getProperty("dummy.admin.username"),
                env.getProperty("dummy.user.username")
        };
    }

    public UserDetailEntity getNewDummyAdminEntity(){
        UserDetailEntity entity = new UserDetailEntity();
        entity.setUserName(env.getProperty("dummy.admin.username"));
        entity.setPassword(env.getProperty("dummy.admin.password"));
        entity.setName("Cardinity Admin 1");
        entity.setEmail(env.getProperty("dummy.admin.email"));
        entity.setDesignation("Dummy System Admin");
        entity.setStatus(Status.ACTIVE.getCode());
        entity.setUserRoleMaps(userRoleMapper.getNewUserRoleMapAsList(entity, SystemUserRole.ADMIN));
        return entity;
    }



    public UserDetailEntity getNewDummyUserEntity(){
        UserDetailEntity entity = new UserDetailEntity();
        entity.setUserName(env.getProperty("dummy.user.username"));
        entity.setPassword(env.getProperty("dummy.user.password"));
        entity.setName("Cardinity Admin 1");
        entity.setEmail(env.getProperty("dummy.user.email"));
        entity.setDesignation("Cardinity Dummy User");
        entity.setStatus(Status.ACTIVE.getCode());
        entity.setUserRoleMaps(userRoleMapper.getNewUserRoleMapAsList(entity, SystemUserRole.USER));
        return entity;
    }
}
