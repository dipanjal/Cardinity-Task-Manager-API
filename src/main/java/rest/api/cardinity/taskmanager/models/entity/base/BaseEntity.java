package rest.api.cardinity.taskmanager.models.entity.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "status")
    private int status;
    @Column(name = "created_at")
    private Date createdAt;
}
