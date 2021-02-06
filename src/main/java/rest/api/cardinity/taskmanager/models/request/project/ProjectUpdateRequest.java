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

    public ProjectUpdateRequest(long projectId, String name, String description, int status) {
        super(name, description, status);
        this.projectId = projectId;
    }
}
