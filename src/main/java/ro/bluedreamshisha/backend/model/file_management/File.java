package ro.bluedreamshisha.backend.model.file_management;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Entity
public class File {

    @Id
    private UUID uuid;

    @NotNull
    private FileCategory category;

    @NotBlank
    private String extension;

    @NotNull
    private LocalDateTime creationTimestamp;

    @NotBlank
    private String url;
}
