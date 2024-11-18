package ro.bluedreamshisha.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.bluedreamshisha.backend.model.i18n.Translation;
import ro.bluedreamshisha.backend.repository.i18n.I18nRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class I18nService {

    private final I18nRepository i18nRepository;

    public List<Translation> findAllByLanguage(String language) {
        return i18nRepository.findAllByLanguage(language);
    }

    public List<Translation> findAllByLanguageAndPrefixList(String language, List<String> prefixList) {
        return i18nRepository.findAllByLanguageAndPrefixList(language, prefixList);
    }

    public void upsert(Translation translation) {
        i18nRepository.save(translation);
    }
}
