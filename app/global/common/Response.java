package global.common;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public final class Response {
    private Object data;
    private int internalStatus = 0; // default:0 is success
    private boolean success;
    private String message;

    public static Response success(Object data) {
        final Response response = new Response();
        response.data = data;
        response.message = "success";
        response.success = true;
        return response;
    }

    public static Response success(final String message, Object data) {
        final Response response = new Response();
        response.data = data;
        response.message = message;
        response.success = true;
        return response;
    }


    public static Response failure(final String errorMessage) {
        final Response response = new Response();
        response.message = errorMessage;
        response.success = false;
        response.internalStatus = 1; // 1: generic failure
        return response;
    }

    public static Response failure(final int statusCode, final String errorMessage) {
        final Response response = new Response();
        response.message = errorMessage;
        response.success = false;
        response.internalStatus = statusCode;
        return response;
    }

}
