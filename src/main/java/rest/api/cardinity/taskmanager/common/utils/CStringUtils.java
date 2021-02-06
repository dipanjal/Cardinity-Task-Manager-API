package rest.api.cardinity.taskmanager.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
public class CStringUtils {
    public static boolean isUpdatable(String str1, String str2){
        if(StringUtils.isEmpty(str2)) return false;
        return StringUtils.equalsIgnoreCase(str1, str2);
    }
}
