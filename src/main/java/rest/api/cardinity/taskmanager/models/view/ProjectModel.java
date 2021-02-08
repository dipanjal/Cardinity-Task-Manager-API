package rest.api.cardinity.taskmanager.models.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author dipanjal
 * @since 2/6/2021
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjectModel implements Serializable {
    private long projectId;
    private String name;
    private String description;
    private String status;
    private String createdAt;
    private String updatedAt;
    private long createdBy;
    private long updatedBy;
//    private List<CardinityUserDetailModel> assignedUsers;
}
