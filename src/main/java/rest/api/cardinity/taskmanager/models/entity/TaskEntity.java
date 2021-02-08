package rest.api.cardinity.taskmanager.models.entity;

import lombok.Getter;
import lombok.Setter;
import rest.api.cardinity.taskmanager.models.entity.base.BaseUpdatableEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@Entity
@Table(name = "TASK")
@Getter
@Setter
public class TaskEntity extends BaseUpdatableEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "expire_at")
    private Date expireAt;

    /*@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "assigned_to")
    private UserDetailEntity assignedTo;
    */

    @Column(name = "assigned_to")
    private long assignedUserId;


    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity projectEntity;
}
