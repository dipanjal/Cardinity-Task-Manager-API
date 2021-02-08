package rest.api.cardinity.taskmanager.models.request.project;

import lombok.NoArgsConstructor;
import rest.api.cardinity.taskmanager.common.validation.groups.UserAction;

import java.util.List;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@NoArgsConstructor
public class ProjectCreationRequest extends BaseProjectRequest {

    public List<String> validate(){
        return super.validate(this, UserAction.CREATE.class);
    }
}
