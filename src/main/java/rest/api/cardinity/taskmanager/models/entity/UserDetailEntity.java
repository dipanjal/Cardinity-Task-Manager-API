package rest.api.cardinity.taskmanager.models.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import rest.api.cardinity.taskmanager.models.entity.base.BaseEntity;

import javax.persistence.*;
import java.util.List;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@Entity
@Table(name = "user_detail")
@Getter
@Setter
public class UserDetailEntity extends BaseEntity {
    @Column(name = "user_name", unique = true, nullable = false)
    private String userName;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "name")
    private String name;
    @Column(name = "designation")
    private String designation;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "userDetailEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<UserRoleMapEntity> userRoleMaps;
}
