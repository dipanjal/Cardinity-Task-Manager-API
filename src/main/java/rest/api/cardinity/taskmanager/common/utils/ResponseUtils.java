package rest.api.cardinity.taskmanager.common.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import rest.api.cardinity.taskmanager.common.enums.ResponseCode;
import rest.api.cardinity.taskmanager.models.response.Response;

import java.util.List;

/**
 * @author dipanjal
 * @since 11/15/2020
 */
public class ResponseUtils {

    public static <T> Response<T> createResponse(int code, String message, T items, Response<T> response){
        response = response == null ? new Response<>() : response;
        response.setResponseCode(code);
        if(StringUtils.isNotBlank(message)) response.getResponseMessages().add(message);
        if(items!=null) response.setItems(items);
        return response;
    }

    public static <T> Response<T> createResponse(int code, String message, Response<T> response){
        response = response == null ? new Response<>() : response;
        response.setResponseCode(code);
        if(StringUtils.isNotBlank(message)) response.getResponseMessages().add(message);
        return response;
    }

    public static <T> Response<T> createResponse(int code, String message){
        Response<T> response = new Response<>();
        response.setResponseCode(code);
        if(StringUtils.isNotBlank(message)) response.getResponseMessages().add(message);
        return response;
    }

    public static  <T> Response<T> createSuccessResponse(T item){
        return createResponse(
                ResponseCode.OPERATION_SUCCESSFUL.getCode(),
                null,
                item,
                null
        );
    }

    public static  <T> Response<T> copyResponse(Response<T> response, Response sourceResponse, Class<T> dataClass){
        response = response == null ? new Response<>() : response;
        response.setResponseCode(sourceResponse.getResponseCode());
        response.setResponseMessages(sourceResponse.getResponseMessages());
        if(sourceResponse.getItems()!=null && dataClass!=null && dataClass.isInstance(sourceResponse.getItems())){
            response.setItems(dataClass.cast(sourceResponse.getItems()));
        }
        return response;
    }

    public static  <T> Response<T> copyResponse(Response<T> response, Response sourceResponse){
        response = response == null ? new Response<>() : response;
        response.setResponseCode(sourceResponse.getResponseCode());
        response.setResponseMessages(sourceResponse.getResponseMessages());
        return response;
    }

    public static  <T> Response<T> copyResponse(Response sourceResponse){
        Response<T> response = new Response<>();
        response.setResponseCode(sourceResponse.getResponseCode());
        response.setResponseMessages(sourceResponse.getResponseMessages());
        return response;
    }


    public static String joinResponseMessage(List<String> violationMessages){
        return CollectionUtils.isNotEmpty(violationMessages)
                ? StringUtils.join(violationMessages, ", ")
                : "";
    }
}
