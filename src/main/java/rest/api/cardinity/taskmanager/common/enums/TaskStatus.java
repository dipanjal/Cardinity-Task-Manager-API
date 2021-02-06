package rest.api.cardinity.taskmanager.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@Getter
@AllArgsConstructor
public enum TaskStatus {

    OPEN(1, "Open"),
    IN_PROGRESS(2, "In Progress"),
    CLOSED(3, "Closed")
    ;

    private int code;
    private String value;

    public static String getValueByCode(int code){
        for(TaskStatus userStatus : TaskStatus.values()){
            if(userStatus.getCode() == code)
                return userStatus.getValue();
        }
        return "";
    }

    public static boolean isValidStatus(int code){
        return Arrays
                .stream(TaskStatus.values())
                .anyMatch( taskStatus -> taskStatus.getCode() == code);
    }

    public static boolean isInvalidValidStatus(int code){
        return !isValidStatus(code);
    }
}
