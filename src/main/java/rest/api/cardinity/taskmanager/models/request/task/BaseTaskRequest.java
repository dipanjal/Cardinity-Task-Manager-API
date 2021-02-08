package rest.api.cardinity.taskmanager.models.request.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import rest.api.cardinity.taskmanager.common.utils.BeanValidator;
import rest.api.cardinity.taskmanager.common.utils.RegexUtils;
import rest.api.cardinity.taskmanager.common.validation.groups.UserAction;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
    @NotEmpty(message = "Task Name can't be empty", groups = UserAction.CREATE.class)
    private String name;
    @NotEmpty(message = "Task Description can't be empty")
    private String description;
    private int expiryHour;
    @Pattern(regexp = RegexUtils.STATUS_VALIDATION_REGEX, flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Invalid Task Status, Must be [open, in progress, closed]")
    private String status;
    private String assignedTo;

    public <T extends BaseTaskRequest, G> List<String> validate(T object, Class<G>... groups){
        return BeanValidator.validateBeanAndGetErrors(object, groups);
    }
}
