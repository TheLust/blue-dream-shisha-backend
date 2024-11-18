package ro.bluedreamshisha.backend.repository.i18n;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.bluedreamshisha.backend.model.i18n.Translation;

import java.util.List;

@Repository
public interface I18nRepository extends JpaRepository<Translation, String>, CriteriaQueryI18nRepository {
    List<Translation> findAllByLanguage(String language);
}
