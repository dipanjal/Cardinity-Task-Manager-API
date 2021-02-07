package rest.api.cardinity.taskmanager.models.request.project;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author dipanjal
 * @since 2/6/2021
 */

@Getter
@Setter
@NoArgsConstructor
public class ProjectUpdateRequest extends BaseProjectRequest {
    @NotNull(message = "Project ID can not be empty")
    @Range(min = 1, message = "Invalid Project ID")
    private long projectId;
}
