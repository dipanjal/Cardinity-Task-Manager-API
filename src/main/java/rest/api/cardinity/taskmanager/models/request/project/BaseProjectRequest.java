package rest.api.cardinity.taskmanager.models.request.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseProjectRequest implements Serializable {
    private String name;
    private String description;
    private int status;
}
