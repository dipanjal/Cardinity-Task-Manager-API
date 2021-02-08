package rest.api.cardinity.taskmanager.common.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import rest.api.cardinity.taskmanager.common.enums.Status;
import rest.api.cardinity.taskmanager.common.enums.SystemUserRole;
import rest.api.cardinity.taskmanager.models.entity.RoleEntity;
import rest.api.cardinity.taskmanager.models.entity.UserDetailEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@Component
@RequiredArgsConstructor
public class DummyObjectMapper {
    private final Environment env;
    private final RoleObjectMapper userRoleMapper;

    public String[] getDummyUserNames(){
        return new String[] {
                env.getProperty("dummy.admin.username"),
                env.getProperty("dummy.user.username"),
                "dummy_user2"
        };
    }

    public UserDetailEntity getNewDummyAdminEntity(RoleEntity role){
        UserDetailEntity entity = new UserDetailEntity();
        entity.setUserName(env.getProperty("dummy.admin.username"));
        entity.setPassword(env.getProperty("dummy.admin.password"));
        entity.setName("Cardinity Admin 1");
        entity.setEmail(env.getProperty("dummy.admin.email"));
        entity.setDesignation("Dummy System Admin");
        entity.setStatus(Status.ACTIVE.getCode());
        entity.setRoles(Collections.singleton(role));
        return entity;
    }

    public List<UserDetailEntity> getNewDummyUserEntityList(RoleEntity role){
        List<UserDetailEntity> dummyUserList = new ArrayList<>();

        UserDetailEntity user1 = new UserDetailEntity();
        user1.setUserName(env.getProperty("dummy.user.username"));
        user1.setPassword(env.getProperty("dummy.user.password"));
        user1.setName("Cardinity User 1");
        user1.setEmail(env.getProperty("dummy.user.email"));
        user1.setDesignation("Cardinity Dummy User");
        user1.setStatus(Status.ACTIVE.getCode());
        user1.setRoles(Collections.singleton(role));

        UserDetailEntity user2 = new UserDetailEntity();
        user2.setUserName("dummy_user2");
        user2.setPassword(env.getProperty("dummy.user.password"));
        user2.setName("Cardinity User 2");
        user2.setEmail("dummyuser2@cardinity.com");
        user2.setDesignation("Cardinity Dummy User 2");
        user2.setStatus(Status.ACTIVE.getCode());
        user2.setRoles(Collections.singleton(role));

        dummyUserList.add(user1);
        dummyUserList.add(user2);
        return dummyUserList;
    }
}
