package rest.api.cardinity.taskmanager.models.entity;

import lombok.Getter;
import lombok.Setter;
import rest.api.cardinity.taskmanager.models.entity.base.BaseUpdatableEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@Entity
@Table(name = "cardinity_project")
@Getter
@Setter
public class ProjectEntity extends BaseUpdatableEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "projects")
    private Set<UserDetailEntity> assignedUsers = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            mappedBy = "projectEntity", orphanRemoval = true)
    private Set<TaskEntity> tasks = new HashSet<>();
}
