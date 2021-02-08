package rest.api.cardinity.taskmanager.repository;

import org.springframework.stereotype.Repository;
import rest.api.cardinity.taskmanager.models.entity.ProjectEntity;

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
}
