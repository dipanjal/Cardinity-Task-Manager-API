package rest.api.cardinity.taskmanager.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dipanjal
 * @since 2/6/2021
 */

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    private int responseCode;
    private List<String> responseMessages;
    private T items;

    public Response() {
        this.responseMessages = new ArrayList<>();
    }

    public Response(T items) {
        this.responseMessages = new ArrayList<>();
        this.items = items;
    }
}