package ro.bluedreamshisha.backend.model.file_management;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class File {

    private UUID uuid;
    private FileCategory category;
    private String extension;
    private LocalDateTime creationTimestamp;
    private String url;
}
