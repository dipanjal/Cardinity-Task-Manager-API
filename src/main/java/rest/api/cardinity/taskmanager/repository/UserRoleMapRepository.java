package rest.api.cardinity.taskmanager.repository;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import rest.api.cardinity.taskmanager.models.entity.UserDetailEntity;
import rest.api.cardinity.taskmanager.models.entity.UserRoleMapEntity;

import java.util.List;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@Repository
public class UserRoleMapRepository extends BaseRepository<UserRoleMapEntity>{
    public UserRoleMapEntity getByUserId(long userId){
        return (UserRoleMapEntity) getCriteria()
                .createAlias("userDetailEntity", "user")
                .add(Restrictions.eq("user.id", userId))
                .setMaxResults(1)
                .uniqueResult();
    }
}
