package ro.bluedreamshisha.backend.rest;

import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.bluedreamshisha.backend.model.request.ExampleRequest;
import ro.bluedreamshisha.backend.model.response.ExampleResponse;

@RestController
@RequestMapping("${api.url.base}/example")
public class ExampleController {

    @PostMapping("/{id}")
    public ExampleResponse example(@PathParam("id") Long id,
                                   @RequestBody ExampleRequest request) {
        return new ExampleResponse();
    }
}
