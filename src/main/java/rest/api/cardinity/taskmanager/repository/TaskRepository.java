package rest.api.cardinity.taskmanager.repository;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import rest.api.cardinity.taskmanager.models.entity.TaskEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@Repository
public class TaskRepository extends BaseRepository<TaskEntity> {

    public Optional<TaskEntity> getOpt(long id){
        return Optional.ofNullable(super.get(id));
    }

    public List<TaskEntity> getByAssignedUserId(long userId){
        return getCriteria()
                .add(Restrictions.eq("assignedUserId", userId))
                .list();
    }

    public List<TaskEntity> getByProjectId(long projectId){
        return getCriteria()
                .createAlias("projectEntity", "project")
                .add(Restrictions.eq("project.id", projectId))
                .list();
    }

    public List<TaskEntity> getByStatus(int status){
        return getCriteria()
                .add(Restrictions.eq("status", status))
                .list();
    }

    public List<TaskEntity> getExpiredTasks(){
        return getCriteria()
                .add(Restrictions.lt("expireAt", new Date()))
                .list();
    }
}
