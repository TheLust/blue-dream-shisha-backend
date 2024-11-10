package ro.bluedreamshisha.backend.model.i18n;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(indexes = @Index(columnList = "code, language"))
@IdClass(TranslationId.class)
public class Translation {

    @Id
    private String code;

    @Id
    private String language;

    @Column(nullable = false)
    private String translation;
}
