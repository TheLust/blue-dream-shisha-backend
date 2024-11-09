package ro.bluedreamshisha.backend.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileManagementUtils {

    public static String getExtension(String fileName) throws IOException {
        if (fileName == null) {
            throw new IOException("File name is null");
        }

        return FilenameUtils.getExtension(fileName);
    }
}
