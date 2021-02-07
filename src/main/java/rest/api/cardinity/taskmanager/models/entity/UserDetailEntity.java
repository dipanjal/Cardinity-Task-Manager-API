package rest.api.cardinity.taskmanager.models.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import rest.api.cardinity.taskmanager.models.entity.base.BaseEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "User_Project",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "project_id") }
    )
    Set<ProjectEntity> projects = new HashSet<>();

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "User_Role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    Collection<RoleEntity> roles = new HashSet<>();
}
