package rest.api.cardinity.taskmanager.models.request.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import rest.api.cardinity.taskmanager.common.utils.BeanValidator;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
public class BaseTaskRequest implements Serializable {
    @NotNull(message = "Project ID can not be empty")
    @Range(min = 1, message = "Invalid Project ID")
    private long projectId;
    @NotEmpty(message = "Assigned Username can't be empty")
    private String assignedTo;
    @NotEmpty(message = "Task Name can't be empty")
    private String name;
    private String description;
    private int expiryHour;
    @Range(min = 1, max = 3, message = "Invalid Project Status")
    private int status;

    public <T extends BaseTaskRequest, G> List<String> validate(T object, Class<G>... groups){
        return BeanValidator.validateBeanAndGetErrors(object, groups);
    }
}
