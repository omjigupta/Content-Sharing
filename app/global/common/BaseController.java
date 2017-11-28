package global.common;

import play.data.FormFactory;
import play.data.validation.ValidationError;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;
import java.util.function.BiFunction;


public class BaseController extends Controller {

    @Inject
    protected FormFactory formFactory;

    public Result index() {
        return ok("application is running");
    }

    public Result success(Response response) {
        return ok(Json.toJson(response));
    }

    public Result success(Object data) {
        final Response response = Response.success(data);
        return ok(Json.toJson(response));
    }

    public Result success(String message, Object data) {
        final Response response = Response.success(message, data);
        return ok(Json.toJson(response));
    }

    public Result failure(String errorMessage) {
        final Response response = Response.failure(errorMessage);
        return badRequest(Json.toJson(response));
    }

    public Result failure(final int internalStatusCode, final String errorMessage) {
        final Response response = Response.failure(internalStatusCode, errorMessage);
        return badRequest(Json.toJson(response));
    }

    public Result failure(final int internalStatusCode, final String errorMessage, final int httpStatus) {
        final Response response = Response.failure(internalStatusCode, errorMessage);
        return status(httpStatus, Json.toJson(response));
    }

    public Result failure(String errorMessage, final int httpStatus) {
        final Response response = Response.failure(errorMessage);
        return status(httpStatus, Json.toJson(response));
    }

    protected String buildValidationErrorMessage(List<ValidationError> validationErrors) {
        BiFunction<String, String, String> validationFormat = (key, errorMessage) -> String.format("{Field:%s,Error:%s }", key, errorMessage);
        return validationErrors.stream().map(error -> validationFormat.apply(error.key(), error.message())).reduce((err1, err2) -> err1 + "," + err2).orElse("NA");
    }

}