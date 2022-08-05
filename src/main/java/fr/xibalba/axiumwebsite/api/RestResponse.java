package fr.xibalba.axiumwebsite.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class RestResponse<T> {

    T data;
    boolean hasError;
    ApiError[] errors;

    public static <T> RestResponse<T> success() {
        return new RestResponse<>(null, false, null);
    }

    public static <T> RestResponse<T> success(T data) {
        return new RestResponse<>(data, false, null);
    }

    public static <T> RestResponse<T> error(ApiError error) {
        return new RestResponse<>(null, true, new ApiError[] { error });
    }

    public static <T> RestResponse<T> errors(ApiError... errors) {
        return new RestResponse<>(null, true, errors);
    }

    public static <T> RestResponse<T> errors(List<ApiError> errors) {
        return new RestResponse<>(null, true, errors.toArray(new ApiError[0]));
    }
}
