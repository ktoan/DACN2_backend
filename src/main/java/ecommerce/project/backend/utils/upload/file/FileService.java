package ecommerce.project.backend.utils.upload.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String uploadImage(MultipartFile uploadFile);
}
