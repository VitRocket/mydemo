package com.github.vitrocket.mydemo.uploader.server.facade;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Vit Rocket on 16.09.2017.
 * @version 1.0
 * @since on 16.09.2017
 */
public interface ImageUploadFacade {

    List<String> upload(MultipartFile[] uploadingFiles) throws IOException;

    File getImage(String imgId, String size);

    boolean delImage(String imgId);
}
