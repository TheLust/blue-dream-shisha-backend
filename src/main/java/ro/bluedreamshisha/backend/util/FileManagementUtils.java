package ro.bluedreamshisha.backend.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileManagementUtils {

    public static File toFile(MultipartFile file, String fileName) throws IOException {
        try {
            File convertedFile = new File(fileName);
            FileOutputStream outputStream = new FileOutputStream(convertedFile);
            outputStream.write(file.getBytes());
            outputStream.close();

            return convertedFile;
        } catch (Exception e) {
            throw new IOException("An error has occurred while converting the file", e);
        }
    }

    public static String getExtension(String fileName) throws IOException {
        if (fileName == null) {
            throw new IOException("File name is null");
        }

        return FilenameUtils.getExtension(fileName);
    }
}
