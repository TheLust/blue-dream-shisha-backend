package ro.bluedreamshisha.backend.facade;

import com.opencsv.CSVReader;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ro.bluedreamshisha.backend.constant.ErrorCode;
import ro.bluedreamshisha.backend.exception.blue_dream_shisha_exception.BlueDreamShishaException;
import ro.bluedreamshisha.backend.model.i18n.Translation;
import ro.bluedreamshisha.backend.model.request.i18n.SearchTranslationsByPrefixListRequest;
import ro.bluedreamshisha.backend.service.I18nService;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class I18nFacade {

    private final I18nService i18nService;

    public Map<String, String> searchTranslationsByPrefixList(
            String language,
            SearchTranslationsByPrefixListRequest request
    ) throws BlueDreamShishaException {
        if (request.getPrefixList().isEmpty()) {
            throw new BlueDreamShishaException(
                    "Prefix list must not by empty",
                    ErrorCode.UNHANDLED,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        List<Translation> translations = i18nService.findAllByLanguageAndPrefixList(language, request.getPrefixList());
        return mapTranslationListToMap(translations);
    }

    public void upsertAllTranslations() throws IOException {
        Resource[] resources = getI18nResources();
        for (Resource resource : resources) {
            if (resource.isReadable()) {
                List<Translation> translations = parseResource(resource);
                for (Translation translation : translations) {
                    i18nService.upsert(translation);
                }
            }
        }
    }

    private Map<String, String> mapTranslationListToMap(List<Translation> translations) {
        Map<String, String> map = new HashMap<>();
        for (Translation translation : translations) {
            map.put(translation.getCode(), translation.getTranslation());
        }

        return map;
    }

    private Resource[] getI18nResources() throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        return resolver.getResources("classpath:i18n/**/*.csv");
    }

    private List<Translation> parseResource(Resource resource) throws IOException {
        List<List<String>> records = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            String[] values;
            while ((values = reader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }
            records.remove(0); // Remove header
        } catch (Exception ex) {
            throw new IOException("Could not parse resource: " + resource.getFilename(), ex);
        }

        List<Translation> translations = new ArrayList<>();
        int index = 0;
        for (List<String> lineArray : records) {
            String[] lineTokens = getLineTokens(resource.getFilename(), lineArray, index);
            translations.add(new Translation(lineTokens[0], lineTokens[1], lineTokens[2]));
            index++;
        }

        return translations;
    }

    private String[] getLineTokens(String filename, List<String> lineArray, int index) throws IOException {
        String line = lineArray.get(0);
        if (line == null) {
            throw new IOException(String.format(
                    "Could not convert lineArray[%s] with index[%s] to record in file[%s] because line is null",
                    lineArray, index, filename
            ));
        }

        String[] lineTokens = line.split("\\|");
        if (lineTokens.length != 3) {
            throw new IOException(String.format(
                    "Could not convert lineArray[%s] with index[%s] to record in file[%s] because line does not have 3 tokens",
                    lineArray, index, filename
            ));
        }

        if (lineTokens[1].length() != 2) {
            throw new IOException(String.format(
                    "Could not convert lineArray[%s] with index[%s] to record in file[%s] because language does not have 2 chars",
                    lineArray, index, filename
            ));
        }
        return lineTokens;
    }
}
