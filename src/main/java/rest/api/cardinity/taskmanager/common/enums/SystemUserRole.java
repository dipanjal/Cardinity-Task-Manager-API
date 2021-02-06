package rest.api.cardinity.taskmanager.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@AllArgsConstructor
@Getter
public enum SystemUserRole {

    USER(1, "USER"),
    ADMIN(2, "ADMIN"),
    ;

    private long code;
    private String value;

    public static String getValueByCode(long code){
        for(SystemUserRole systemUserRole : SystemUserRole.values()){
            if(systemUserRole.getCode() == code)
                return systemUserRole.getValue();
        }
        return "";
    }

    private static boolean isValidRole(long code){
        return Arrays
                .stream(SystemUserRole.values())
                .anyMatch(systemUserRole -> systemUserRole.getCode() == code);
    }

    private static boolean isInvalidRole(long code){
        return !isValidRole(code);
    }
}
