package rest.api.cardinity.taskmanager.models.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import rest.api.cardinity.taskmanager.models.entity.base.BaseUpdatableEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@Entity
@Table(name = "TASK")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class TaskEntity extends BaseUpdatableEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "expire_at")
    private Date expireAt;
    @Column(name = "assigned_to")
    private long assignedUserId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            optional = false)
    @JoinColumn(name = "project_id", nullable = false, updatable = false)
    private ProjectEntity projectEntity;
}
