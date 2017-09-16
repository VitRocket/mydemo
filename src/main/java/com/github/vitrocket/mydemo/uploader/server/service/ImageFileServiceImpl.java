package com.github.vitrocket.mydemo.uploader.server.service;

import com.github.vitrocket.mydemo.uploader.server.model.ImageFile;
import com.github.vitrocket.mydemo.uploader.server.repository.ImageFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Vit Rocket on 16.09.2017.
 * @version 1.0
 * @since on 16.09.2017
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ImageFileServiceImpl implements ImageFileService {

    private final ImageFileRepository imageFileRepository;

    @Override
    public ImageFile findById(String id) {
        ImageFile imageFile = imageFileRepository.getImageFile(id);
        return imageFile;
    }

    @Override
    public String saveFile(ImageFile imageFile) {
        return imageFileRepository.saveImageFile(imageFile);
    }

    @Override
    public void deleteFileById(String id) {
        imageFileRepository.deleteImageFile(id);
    }
}
