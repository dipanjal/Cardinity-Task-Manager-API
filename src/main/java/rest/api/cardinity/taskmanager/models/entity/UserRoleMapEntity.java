package rest.api.cardinity.taskmanager.models.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import rest.api.cardinity.taskmanager.models.entity.base.BaseEntity;

import javax.persistence.*;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@Entity
@Table(name = "USER_ROLE_MAP")
@Getter
@Setter
public class UserRoleMapEntity extends BaseEntity {

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserDetailEntity userDetailEntity;
    private long roleId;
}
