package ro.bluedreamshisha.backend.rest;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.bluedreamshisha.backend.constant.ErrorCode;
import ro.bluedreamshisha.backend.exception.BlueDreamShishaException;
import ro.bluedreamshisha.backend.facade.I18nFacade;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("${api.url.base}/i18n")
@RequiredArgsConstructor
public class I18nController {

    private final I18nFacade i18nFacade;

    @GetMapping("/languages/{language}")
    @Operation(operationId = "get-translations")
    public Map<String, String> findTranslations(@PathVariable("language") String language) {
        return i18nFacade.findTranslations(language);
    }

    @PatchMapping("/upsert")
    @Operation(operationId = "upsert-translations")
    public ResponseEntity<String> upsertTranslations() throws BlueDreamShishaException {
        try {
            i18nFacade.upsertAllTranslations();
        } catch (IOException e) {
            throw new BlueDreamShishaException(
                    "Could not upsert translations",
                    ErrorCode.UNHANDLED_ERROR,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e
            );
        }
        return new ResponseEntity<>(
                "OK",
                HttpStatus.OK
        );
    }
}
