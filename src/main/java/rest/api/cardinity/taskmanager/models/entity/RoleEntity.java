package rest.api.cardinity.taskmanager.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rest.api.cardinity.taskmanager.models.entity.UserDetailEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author dipanjal
 * @since 2/8/2021
 */

@Entity
@Table(name = "ROLE")
@Getter
@Setter
@NoArgsConstructor
public class RoleEntity implements Serializable {
    @Id
    private long id;
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<UserDetailEntity> users = new HashSet<>();

    public RoleEntity(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
