package ro.bluedreamshisha.backend.client;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ro.bluedreamshisha.backend.exception.FileManagementException;
import ro.bluedreamshisha.backend.model.file_management.File;
import ro.bluedreamshisha.backend.model.file_management.FileCategory;
import ro.bluedreamshisha.backend.util.FileManagementUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class FileManagementClient {

    @Value("${file-management.bucket-id}")
    private String bucket;

    private final Storage storage;

    public FileManagementClient() {
        this.storage = StorageOptions.getDefaultInstance().getService();
    }

    public byte[] find(File file) throws FileManagementException {
        try {
            BlobId blobId = BlobId.of(
                    bucket,
                    file.getCategory().getValue() + "/" + file.getUuid()
            );
            List<Blob> blobs = storage.get(new BlobId[]{blobId});

            if (blobs != null && !blobs.isEmpty()) {
                return blobs.get(0).getContent();
            }
        } catch (Exception e) {
            throw new FileManagementException("Error while finding file in storage service", e);
        }

        throw new FileManagementException("File not found in storage service");
    }

    public File create(
            @NonNull MultipartFile file,
            @NonNull FileCategory category
    ) throws FileManagementException {
        try {
            String fileName = file.getOriginalFilename();

            UUID uuid = UUID.randomUUID();
            String extension = FileManagementUtils.getExtension(fileName);

            byte[] fileData = file.getBytes();
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
            throw new FileManagementException("Error while saving file to storage service", e);
        }

        throw new FileManagementException("Null blob received back from storage service");
    }
}
