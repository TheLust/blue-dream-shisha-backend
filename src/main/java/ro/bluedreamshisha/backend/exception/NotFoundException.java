package ro.bluedreamshisha.backend.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class NotFoundException extends Exception {

    private final List<String> searchParams;

    public NotFoundException(List<String> searchParams) {
        this.searchParams = searchParams;
    }
}
