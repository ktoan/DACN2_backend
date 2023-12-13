package ecommerce.project.backend.services.impl;

import ecommerce.project.backend.entities.Image;
import ecommerce.project.backend.repositories.ImageRepository;
import ecommerce.project.backend.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    @Override
    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }
}
