package rest.api.cardinity.taskmanager.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import rest.api.cardinity.taskmanager.models.response.Response;

/**
 * @author dipanjal
 * @since 2/6/2021
 */

@Getter
@AllArgsConstructor
public enum ResponseCode {

    OPERATION_SUCCESSFUL(200),
    BAD_REQUEST(403),
    RECORD_NOT_FOUND(404),
    UNAUTHORIZED(401),
    RUNTIME_ERROR(500),
    REMOTE_ERROR(501),
    INTERNAL_ERROR(502),
    ;

    private final int code;

    public static boolean isSuccessful(Response response){
        return response!= null && ResponseCode.OPERATION_SUCCESSFUL.code == response.getResponseCode();
    }

    public static boolean isNotSuccessful(Response response){
        return !isSuccessful(response);
    }
}
