package ro.bluedreamshisha.backend.model.file_management;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FileCategory {

    PUBLIC("public"),
    PLATFORM("platform");

    private final String value;
}
