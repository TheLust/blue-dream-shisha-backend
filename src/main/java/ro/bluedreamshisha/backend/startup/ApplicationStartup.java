package ro.bluedreamshisha.backend.startup;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ro.bluedreamshisha.backend.facade.I18nFacade;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ApplicationStartup {

    private final I18nFacade i18nFacade;

    @EventListener(ApplicationReadyEvent.class)
    public void startup() throws IOException {
        i18nFacade.upsertAllTranslations();
    }
}
