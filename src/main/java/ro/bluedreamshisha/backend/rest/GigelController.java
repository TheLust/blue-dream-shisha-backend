package ro.bluedreamshisha.backend.rest;

import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.bluedreamshisha.backend.model.request.ExampleRequest;
import ro.bluedreamshisha.backend.model.request.GigelRequest;
import ro.bluedreamshisha.backend.model.response.ExampleResponse;
import ro.bluedreamshisha.backend.model.response.GigelResponse;

@RestController
@RequestMapping("${api.url.base}/gigel")
public class GigelController {

    @PostMapping("/{id}")
    public GigelResponse example(@PathParam("id") Long id,
                                 @RequestBody GigelRequest request) {
        return new GigelResponse();
    }
}
