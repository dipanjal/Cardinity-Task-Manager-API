package rest.api.cardinity.taskmanager.models.request.project;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author dipanjal
 * @since 2/6/2021
 */

@Getter
@Setter
@NoArgsConstructor
public class ProjectUpdateRequest extends BaseProjectRequest {
    private long projectId;
}
