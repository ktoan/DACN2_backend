package ecommerce.project.backend.utils.upload.file;

import com.cloudinary.Cloudinary;
import ecommerce.project.backend.exceptions.ServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final Cloudinary cloudinary;

    @Override
    public String uploadImage(MultipartFile uploadFile) {
        try {
            return cloudinary.uploader().upload(uploadFile.getBytes(), Map.of("public_id", UUID.randomUUID().toString() + System.currentTimeMillis(), "folder", "ecommerce/avatars")).get("url").toString();
        } catch (IOException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }
}
