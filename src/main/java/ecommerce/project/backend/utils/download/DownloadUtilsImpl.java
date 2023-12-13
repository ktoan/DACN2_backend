package ecommerce.project.backend.utils.download;

import ecommerce.project.backend.exceptions.ServerErrorException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * @author Toan Nguyen Khanh
 * @version 1.0
 */
@Component
public class DownloadUtilsImpl implements DownloadUtils {
    @Override
    public String downloadVerifiedImage(String username, String url) {
        try {
            URL accessUrl = new URL(url);
            InputStream inputStream = accessUrl.openStream();
            String destinationPath = "/images/verified/" + username + ".jpg";
            Path destination = Path.of(destinationPath);
            Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);
            return destinationPath;
        } catch (IOException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }
}
