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
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "created_by", nullable = false)
    private UserDetailEntity createdBy;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "updated_by", nullable = false)
    private UserDetailEntity updatedBy;

    @Column(name = "updated_at")
    private Date updatedAt;

}
