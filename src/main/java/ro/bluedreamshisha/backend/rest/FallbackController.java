package ro.bluedreamshisha.backend.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FallbackController {

    private final ResourceLoader resourceLoader;

    @GetMapping("/**/{path:[^\\.]*}")
    public Resource redirectApi() {
        return resourceLoader.getResource("classpath:/static/index.html");
    }
}
