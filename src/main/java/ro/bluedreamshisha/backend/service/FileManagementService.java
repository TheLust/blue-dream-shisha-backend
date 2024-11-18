package ro.bluedreamshisha.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ro.bluedreamshisha.backend.client.FileManagementClient;
import ro.bluedreamshisha.backend.constant.ErrorCode;
import ro.bluedreamshisha.backend.exception.blue_dream_shisha_exception.BlueDreamShishaException;
import ro.bluedreamshisha.backend.model.file_management.File;
import ro.bluedreamshisha.backend.model.file_management.FileCategory;
import ro.bluedreamshisha.backend.repository.FileRepository;

@Service
@RequiredArgsConstructor
public class FileManagementService {

    private final FileRepository fileRepository;

    private final FileManagementClient fileManagementClient;

    public File insert(
            MultipartFile multipartFile,
            FileCategory fileCategory
    ) throws BlueDreamShishaException {
        try {
            File file = fileManagementClient.create(multipartFile, fileCategory);
            return fileRepository.save(file);
        } catch (Exception e) {
            throw new BlueDreamShishaException(
                    "Could not upload file in file-management",
                    ErrorCode.UNHANDLED,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e
            );
        }
    }
}
