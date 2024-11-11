package ro.bluedreamshisha.backend.constant;

public interface ErrorCode {

    String PREFIX = "ERROR_";

    String UNHANDLED_ERROR = PREFIX + "UNHANDLED_ERROR";
    String NOT_FOUND = PREFIX + "NOT_FOUND";
    String UNAUTHORIZED = PREFIX + "UNAUTHORIZED";
    String ACCESS_DENIED = PREFIX + "ACCESS_DENIED";
    String BAD_CREDENTIALS = PREFIX + "BAD_CREDENTIALS";
    String CHECK_ERROR = PREFIX + "CHECK_ERROR";
}
