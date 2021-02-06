package rest.api.cardinity.taskmanager.repository;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import rest.api.cardinity.taskmanager.models.entity.ProjectEntity;
import rest.api.cardinity.taskmanager.models.entity.UserRoleMapEntity;

import java.util.List;
import java.util.Optional;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@Repository
public class ProjectRepository extends BaseRepository<ProjectEntity> {

    public Optional<ProjectEntity> getOpt(long id){
        return Optional.ofNullable(super.get(id));
    }

    public List<ProjectEntity> getByUserName(String userName){
        return getCriteria()
                .createAlias("createdBy", "user")
                .add(Restrictions.eq("user.userName", userName))
                .list();
    }
}
