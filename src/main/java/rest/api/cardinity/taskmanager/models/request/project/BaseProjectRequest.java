package rest.api.cardinity.taskmanager.models.request.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rest.api.cardinity.taskmanager.common.utils.BeanValidator;
import rest.api.cardinity.taskmanager.common.utils.RegexUtils;
import rest.api.cardinity.taskmanager.common.validation.groups.UserAction;

import javax.validation.constraints.NotEmpty;
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
public abstract class BaseProjectRequest implements Serializable {
    @NotEmpty(message = "Project Name can't be empty", groups = UserAction.CREATE.class)
    private String name;
    private String description;
    @Pattern(regexp = RegexUtils.STATUS_VALIDATION_REGEX, flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Invalid Project Status, Must be [open, in progress, closed]")
    private String status;
    private String assignedTo;

    public <T extends BaseProjectRequest, G> List<String> validate(T object, Class<G>... groups){
        return BeanValidator.validateBeanAndGetErrors(object, groups);
    }

}
