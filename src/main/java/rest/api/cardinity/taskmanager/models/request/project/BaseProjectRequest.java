package rest.api.cardinity.taskmanager.models.request.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import rest.api.cardinity.taskmanager.common.utils.BeanValidator;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author dipanjal
 * @since 2/6/2021
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseProjectRequest implements Serializable {
    @NotEmpty(message = "Project Name can't be empty")
    private String name;
    private String description;
    @Range(min = 1, max = 4, message = "Invalid Project Status")
    private int status;
    @NotEmpty(message = "Assigned Username can't be empty")
    private String assignedTo;

    public <T extends BaseProjectRequest, G> List<String> validate(T object, Class<G>... groups){
        return BeanValidator.validateBeanAndGetErrors(object, groups);
    }

}
