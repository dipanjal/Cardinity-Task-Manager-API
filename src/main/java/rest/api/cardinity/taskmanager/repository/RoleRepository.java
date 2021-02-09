package rest.api.cardinity.taskmanager.repository;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import rest.api.cardinity.taskmanager.models.entity.RoleEntity;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@Repository
public class RoleRepository extends BaseRepository<RoleEntity>{
    public RoleEntity getByUserId(long userId){
        return (RoleEntity) getCriteria()
                .createAlias("userDetailEntity", "user")
                .add(Restrictions.eq("user.id", userId))
                .setMaxResults(1)
                .uniqueResult();
    }
}
