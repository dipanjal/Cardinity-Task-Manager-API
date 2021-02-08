package rest.api.cardinity.taskmanager.models.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

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
    private String createdBy;
    private String updatedBy;
    private String assignedTo;
    private ProjectModel project;
}
