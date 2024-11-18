package ro.bluedreamshisha.backend.repository.i18n;

import ro.bluedreamshisha.backend.model.i18n.Translation;

import java.util.List;

public interface CriteriaQueryI18nRepository {
    List<Translation> findAllByLanguageAndPrefixList(String language, List<String> prefixList);
}
