package rest.api.cardinity.taskmanager.repository;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import rest.api.cardinity.taskmanager.models.entity.UserDetailEntity;

import java.util.List;
import java.util.Optional;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@Repository
public class UserDetailRepository extends BaseRepository<UserDetailEntity>{

    public List<UserDetailEntity> getByUserNames(String ... dummyUserNames){
        return getCriteria()
                .add(Restrictions.in("userName", dummyUserNames))
                .list();
    }

    public UserDetailEntity getByUserName(String userName){
        return (UserDetailEntity) getCriteria()
                .add(Restrictions.eq("userName", userName))
                .setMaxResults(1)
                .uniqueResult();
    }

    public Optional<UserDetailEntity> getByUserNameOpt(String userName){
         return Optional.ofNullable(this.getByUserName(userName));
    }

}
