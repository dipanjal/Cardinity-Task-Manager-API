package rest.api.cardinity.taskmanager.models.request.project;

import lombok.NoArgsConstructor;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@NoArgsConstructor
public class ProjectCreationRequest extends BaseProjectRequest {

    public ProjectCreationRequest(String name, String description, int status) {
        super(name, description, status);
    }
}
