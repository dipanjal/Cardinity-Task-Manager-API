package rest.api.cardinity.taskmanager.models.entity.base;

import lombok.Getter;
import lombok.Setter;
import rest.api.cardinity.taskmanager.models.entity.UserDetailEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BaseUpdatableEntity extends BaseEntity {
    /*@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "created_by")
    private UserDetailEntity createdBy;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "updated_by")
    private UserDetailEntity updatedBy;*/

    @Column(name = "created_by")
    private long createdById;

    @Column(name = "updated_by")
    private long updatedById;

    @Column(name = "updated_at")
    private Date updatedAt;

}
