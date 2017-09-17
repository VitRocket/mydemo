package com.github.vitrocket.mydemo.uploader.server.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Vit Rocket on 16.09.2017.
 * @version 1.0
 * @since on 16.09.2017
 */
public interface ImageFileService {

    void saveImageFile(MultipartFile uploadedFile) throws IOException;
}
