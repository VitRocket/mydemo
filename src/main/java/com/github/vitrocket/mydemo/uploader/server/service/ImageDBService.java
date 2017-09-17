package com.github.vitrocket.mydemo.uploader.server.service;

import com.github.vitrocket.mydemo.uploader.server.model.ImageFile;

/**
 * @author Vit Rocket on 16.09.2017.
 * @version 1.0
 * @since on 16.09.2017
 */
public interface ImageDBService {

    ImageFile findById(String id);

    String saveFile(ImageFile imageFile);

    void deleteFileById(String id);

}
