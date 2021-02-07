package rest.api.cardinity.taskmanager.models.request.task;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@NoArgsConstructor
@Getter
@Setter
public class TaskUpdateRequest extends BaseTaskRequest {
    @NotNull(message = "Task ID can not be empty")
    @Range(min = 1, message = "Invalid Task ID")
    private long taskId;
}
