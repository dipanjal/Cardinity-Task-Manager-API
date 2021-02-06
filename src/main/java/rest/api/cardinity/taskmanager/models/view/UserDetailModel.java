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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailModel implements Serializable {
    private String userName;
    private String fullName;
    private String email;
    private String userRoles;
    private String designation;
    private String status;
}
