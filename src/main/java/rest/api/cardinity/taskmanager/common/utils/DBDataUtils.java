package rest.api.cardinity.taskmanager.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
public class DBDataUtils {
    public static boolean isUpdatable(String str1, String str2){
        if(StringUtils.isEmpty(str2)) return false;
        return !StringUtils.equalsIgnoreCase(str1, str2);
    }

    public static boolean isUpdatable(int value1, int value2){
        if(value1 <= 0) return false;
        return (value1 != value2);
    }
}
