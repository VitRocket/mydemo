package com.github.vitrocket.mydemo.uploader.server.repository;

import com.github.vitrocket.mydemo.uploader.server.model.ImageFile;
import com.mongodb.WriteResult;

import java.util.List;

/**
 * @author Vit Rocket on 16.09.2017.
 * @version 1.0
 * @since on 16.09.2017
 */
public interface ImageFileRepository {

    List<ImageFile> getAllImageFiles();

    String saveImageFile(ImageFile imageFile);

    ImageFile getImageFile(String id);

    WriteResult updateImageFile(String id, String name);

    void deleteImageFile(String id);

}