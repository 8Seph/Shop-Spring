package ru.mystore.store.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.mystore.store.exceptions.ImageExtensionException;
import ru.mystore.store.persistence.entities.Image;
import ru.mystore.store.persistence.entities.Product;
import ru.mystore.store.persistence.repositories.ImageRepository;
import ru.mystore.store.utils.Validators;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private static final String IMAGES_PATH = "C:/Users/Seph/OneDrive/Java projects/store/storage/images/product/";
    private static final String ICONS_STATIC_PATH = "/static/icons/";

    private final ImageRepository imageRepository;

    private String getImageNameByProductId(UUID id) {
        return imageRepository.obtainImageNameByProductId(id);
    }

    public BufferedImage loadProductImage(String id) {
        String imageName = null;
        Path filePath = null;

        try {
            if (Validators.isUUID(id)) {
                imageName = getImageNameByProductId(UUID.fromString(id));
                filePath = Paths.get(IMAGES_PATH + imageName);

                if (filePath == null) {
                    imageName = "no_img.png";
                    Resource resource = new ClassPathResource(ICONS_STATIC_PATH + imageName);
                    filePath = resource.getFile().toPath();
                }
            }

            return ImageIO.read(new UrlResource(filePath.toUri()).getFile());

        } catch (IOException ex) {
            log.error("Image wasn't found!", imageName);
            return null;
        }
    }


    @Transactional
    public Image uploadProductImage(MultipartFile multipartFile, Product product) throws IOException {
        String imageName = product.getTitle() + checkImageExtension(multipartFile);
        Path imageDir = Paths.get(IMAGES_PATH + imageName);
        Files.copy(multipartFile.getInputStream(), imageDir, StandardCopyOption.REPLACE_EXISTING);
        return imageRepository.save(new Image(imageName));
    }

    @SneakyThrows
    private String checkImageExtension(MultipartFile multipartFile) {
        String string = multipartFile.getContentType();
        if (string.equals("image/png")) return ".png";
        if (string.equals("image/jpg")) return ".jpg";
        throw  new ImageExtensionException("Не верное расширение файла");
    }

}