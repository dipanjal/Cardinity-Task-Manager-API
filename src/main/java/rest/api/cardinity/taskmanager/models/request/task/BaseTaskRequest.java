package rest.api.cardinity.taskmanager.models.request.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author dipanjal
 * @since 2/6/2021
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseTaskRequest implements Serializable {
    private long projectId;
    private String assignedTo;
    private String name;
    private String description;
    private int expiryHour;
    private int status;
}
