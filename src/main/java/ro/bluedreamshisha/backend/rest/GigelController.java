package ro.bluedreamshisha.backend.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ro.bluedreamshisha.backend.client.FileManagementClient;
import ro.bluedreamshisha.backend.model.file_management.File;
import ro.bluedreamshisha.backend.model.file_management.FileCategory;

@RestController
@RequestMapping("${api.url.base}/gigel")
@RequiredArgsConstructor
public class GigelController {

    private final FileManagementClient fileManagementClient;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public File example(@RequestPart("file") MultipartFile file) {
        return fileManagementClient.upload(file, FileCategory.PLATFORM);
    }
}
