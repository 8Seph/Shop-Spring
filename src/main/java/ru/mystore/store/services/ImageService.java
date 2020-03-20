package ru.mystore.store.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.mystore.store.persistence.repositories.ImageRepository;


import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.charset.MalformedInputException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    private String getFirstImageNameByProductId(UUID id) {
        return imageRepository.getFirstImageByProductId(id);
    }

    private List<String> getAllImageNamesByProductId(UUID id) {
        return imageRepository.getAllImagesByProductId(id);
    }


    public BufferedImage loadFirstImage(String product_id) throws IOException {
        try {
            String imageName = getFirstImageNameByProductId(UUID.fromString(product_id));
            Resource resource = new ClassPathResource("/static/images/" + imageName);
            if (resource.exists()) {
                return ImageIO.read(resource.getFile());
            } else {
                log.error("Image not found!");
                throw new FileNotFoundException("File " + imageName + " not found!");
            }
        } catch (MalformedInputException | FileNotFoundException ex) {
            return null;
        }
    }


//    public List<BufferedImage> loadAllImages(String product_id) throws IOException {
//        List<BufferedImage> bufferedImages = new ArrayList<>();
//        try {
//            List<String> imageNames = getAllImageNamesByProductId(UUID.fromString(product_id));
//            for (int i = 0; i < imageNames.size(); i++) {
//                Resource resource = new ClassPathResource("/static/images/" + imageNames.get(i));
//                if (resource.exists()) {
//                    bufferedImages.add(ImageIO.read(resource.getFile()));
//                } else {
//                    log.error("Image not found!");
//                    throw new FileNotFoundException("File " + imageNames.get(i) + " not found!");
//                }
//
//            }
//
//        } catch (MalformedInputException | FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        return (bufferedImages.size() > 0) ? bufferedImages : null;
//
//    }

}