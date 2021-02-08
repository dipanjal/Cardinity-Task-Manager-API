package rest.api.cardinity.taskmanager.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@Getter
@AllArgsConstructor
public enum Status {

    ACTIVE(1, "Active"),
    CLOSED(2, "Closed"),
    SUSPENDED(3, "Suspended"),
    BLOCKED(4, "Blocked");

    private int code;
    private String value;

    public static String getValueByCode(int code){
        for(Status status : Status.values()){
            if(status.getCode() == code)
                return status.getValue();
        }
        return "";
    }

    public static boolean isActive(int code){
        return (Status.ACTIVE.getCode() == code);
    }

    public static boolean isInactive(int code){
        return !isActive(code);
    }
}
