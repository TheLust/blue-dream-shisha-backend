package ro.bluedreamshisha.backend.client;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.NonNull;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ro.bluedreamshisha.backend.model.file_management.File;
import ro.bluedreamshisha.backend.model.file_management.FileCategory;
import ro.bluedreamshisha.backend.util.FileManagementUtils;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class FileManagementClient {

    @Value("${file-management.bucket-id}")
    private String bucket;

    private final Storage storage;

    public FileManagementClient() {
        this.storage = StorageOptions.getDefaultInstance().getService();
    }

    public File upload(@NonNull MultipartFile file,
                       @NonNull FileCategory category) {
        try {
            String fileName = file.getOriginalFilename();

            UUID uuid = UUID.randomUUID();
            String extension = FileManagementUtils.getExtension(fileName);

            byte[] fileData = FileUtils.readFileToByteArray(FileManagementUtils.toFile(file, uuid.toString()));
            BlobId blobId = BlobId.of(
                    bucket,
                    category.getValue() + "/" + uuid
            );
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
            Blob blob = storage.create(blobInfo, fileData);

            if (blob != null) {
                return new File(
                        uuid,
                        category,
                        extension,
                        LocalDateTime.now(),
                        blob.getMediaLink()
                );
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }
}
