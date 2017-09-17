package com.github.vitrocket.mydemo.uploader.server.facade;

import com.github.vitrocket.mydemo.uploader.server.model.ImageFile;
import com.github.vitrocket.mydemo.uploader.server.model.ImageFileResize;
import com.github.vitrocket.mydemo.uploader.server.model.ImageSize;
import com.github.vitrocket.mydemo.uploader.server.service.ImageDBService;
import com.github.vitrocket.mydemo.uploader.server.service.ImageFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vit Rocket on 16.09.2017.
 * @version 1.0
 * @since on 16.09.2017
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ImageUploadFacadeImpl implements ImageUploadFacade {

    private final ImageFileService imageFileService;
    private final ImageDBService imageDBService;

    private static final String STORAGEIMAGE = System.getProperty("user.dir") + "/storage/image/";
    private final long limitSize = 5000000L;

    @Override
    public List<String> upload(MultipartFile[] uploadingFiles) throws IOException {
        List<String> fileIds = new ArrayList<>();
        for (MultipartFile uploadedFile : uploadingFiles) {
            log.info(String.valueOf(uploadedFile.getSize()));
            if (uploadedFile.getSize() > limitSize) {
                fileIds.add(uploadedFile.getOriginalFilename() + " exceededs limit size");
            } else {
                fileIds.add(uploadFile(uploadedFile));
            }
        }
        return fileIds;
    }

    private String uploadFile(MultipartFile uploadedFile) throws IOException {

        imageFileService.saveImageFile(uploadedFile);

        String fileName = uploadedFile.getOriginalFilename();
        String fileExtension = FilenameUtils.getExtension(fileName);

        String localOriginalUrl = "original/" + fileName;
        String localSmallUrl = "small/" + fileName;
        String localMediumUrl = "medium/" + fileName;
        String localBigUrl = "big/" + fileName;

        List<ImageFileResize> fileResizes = new ArrayList<>();
        fileResizes.add(new ImageFileResize(fileName, localSmallUrl, ImageSize.SMALL));
        fileResizes.add(new ImageFileResize(fileName, localMediumUrl, ImageSize.MEDIUM));
        fileResizes.add(new ImageFileResize(fileName, localBigUrl, ImageSize.BIG));
        ImageFile imageFile = ImageFile.builder()
                                       .localUrl(localOriginalUrl)
                                       .loadDateTime(LocalDateTime.now())
                                       .originalName(fileName)
                                       .format(fileExtension)
                                       .imageResizes(fileResizes)
                                       .build();
        return imageDBService.saveFile(imageFile);
    }

    @Override
    public File getImage(String imgId, String size) {
        ImageFile imageFile = imageDBService.findById(imgId);
        if (imageFile == null) {
            return null;
        }
        ImageFileResize imageFileResize = imageFile.getImageResizes().stream()
                                                   .filter(imgFile -> imgFile.getImageSize().toString().equals(size))
                                                   .findFirst().orElse(null);
        String imageUrl = imageFileResize.getLocalUrl();
        return new File(STORAGEIMAGE + imageUrl);
    }

    @Override
    public boolean delImage(String imgId) {
        ImageFile imageFile = imageDBService.findById(imgId);
        if(imageFile == null){
            return false;
        }
        for (ImageFileResize imageFileResize : imageFile.getImageResizes()) {
            File file = new File(STORAGEIMAGE + imageFileResize.getLocalUrl());
            if (file.delete()) {
                System.out.println(file.getName() + " is deleted!");
            } else {
                System.out.println("Delete operation is failed.");
            }
        }
        File file = new File(STORAGEIMAGE + imageFile.getLocalUrl());
        if (file.delete()) {
            System.out.println(file.getName() + " is deleted!");
        } else {
            System.out.println("Delete operation is failed.");
        }
        imageDBService.deleteFileById(imgId);
        return true;
    }

}
