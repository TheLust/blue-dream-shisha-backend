package ro.bluedreamshisha.backend.exception;

public class FileManagementException extends Exception {

    public FileManagementException(String message) {
        super(message);
    }

    public FileManagementException(String message, Throwable cause) {
        super(message, cause);
    }
}
