package ro.bluedreamshisha.backend.facade;

import com.opencsv.CSVReader;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import ro.bluedreamshisha.backend.model.i18n.Translation;
import ro.bluedreamshisha.backend.service.I18nService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class I18nFacade {

    private final I18nService i18nService;
    private final ResourceLoader resourceLoader;

    public Map<String, String> findTranslations(String language) {
        List<Translation> translations = i18nService.findAllByLanguage(language);
        Map<String, String> map = new HashMap<>();
        for (Translation translation : translations) {
            map.put(translation.getCode(), translation.getTranslation());
        }

        return map;
    }

    public void upsertAllTranslations() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:i18n");
        File i18nDirectory = resource.getFile();
        if (i18nDirectory.exists()) {
            List<File> files = getFileListFromFolder(i18nDirectory);
            for (File file : files) {
                List<Translation> translations = parseFile(file);
                for (Translation translation : translations) {
                    i18nService.upsert(translation);
                }
            }
        }
    }

    private List<Translation> parseFile(File file) throws IOException {
        List<List<String>> records = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(file)))) {
            String[] values;
            while ((values = reader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }
            records.remove(0); //remove header
        } catch (Exception ex) {
            throw new IOException("Could not parse file: " + file.getName());
        }

        List<Translation> translations = new ArrayList<>();
        int index = 0;
        for (List<String> lineArray : records) {
            String[] lineTokens = getLineTokens(file, lineArray, index);

            translations.add(new Translation(lineTokens[0], lineTokens[1], lineTokens[2]));
            index++;
        }

        return translations;
    }

    private String[] getLineTokens(File file, List<String> lineArray, int index) throws IOException {
        String line = lineArray.get(0);
        if (line == null) {
            throw new IOException(String.format(
                    "Could not convert lineArray[%s] with index[%s] to record in file[%s] because line is null",
                    lineArray,
                    index,
                    file.getName()
            ));
        }

        String[] lineTokens = line.split("\\|");
        if (lineTokens.length != 3) {
            throw new IOException(String.format(
                    "Could not convert lineArray[%s] with index[%s] to record in file[%s] because line does not have 3 tokens",
                    lineArray,
                    index,
                    file.getName()
            ));
        }

        if (lineTokens[1].length() != 2) {
            throw new IOException(String.format(
                    "Could not convert lineArray[%s] with index[%s] to record in file[%s] because language does not have 2 chars",
                    lineArray,
                    index,
                    file.getName()
            ));
        }
        return lineTokens;
    }

    private List<File> getFileListFromFolder(File file) {
        List<File> files = new ArrayList<>();
        if (file.isFile()) {
            if (file.getName().endsWith(".csv")) {
                return List.of(file);
            }
            return List.of();
        } else {
            File[] filesInDir = file.listFiles();
            if (filesInDir == null) {
                return List.of();
            }

            for (File fileInDir : filesInDir) {
                files.addAll(getFileListFromFolder(fileInDir));
            }
        }

        return files;
    }
}
