package ro.bluedreamshisha.backend.constant;

public interface SwaggerDescription {

    String HTTP_200 = "OK";
    String HTTP_400 = "Specific expected error, handled by the api";
    String HTTP_401 = "User must be authenticated to access the resource";
    String HTTP_403 = "User is authenticated but does not have access to resource";
    String HTTP_404 = "Targeted resource does not exist";
    String HTTP_500 = "Unexpected error, unhandled by the api or the service is unavailable at the moment";
}
