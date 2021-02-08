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
public class TaskModel implements Serializable {
    private long taskId;
    private String name;
    private String description;
    private String status;
    private String createdAt;
    private String updatedAt;
    private String expireAt;
    private long createdBy;
    private long updatedBy;
    private long assignedTo;
    private ProjectModel project;

}
