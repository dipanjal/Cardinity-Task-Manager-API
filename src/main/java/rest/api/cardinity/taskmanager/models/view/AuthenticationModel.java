package rest.api.cardinity.taskmanager.models.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author dipanjal
 * @since 2/7/2021
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationModel implements Serializable {
    private String token;
}
