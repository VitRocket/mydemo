package com.github.vitrocket.mydemo.uploader.server.controller;

import com.github.vitrocket.mydemo.uploader.server.facade.ImageUploadFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.List;

/**
 * @author Vit Rocket on 16.09.2017.
 * @version 1.0
 * @since on 16.09.2017
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/image-storage")
public class ImageFileController {

    private final ImageUploadFacade imageUploadFacade;

    @PostMapping(value = "/upload-file/")
    public ResponseEntity<List<String>> uploadImages(@RequestParam("uploadingFiles") MultipartFile[] uploadingFiles) {
        try {
            return ResponseEntity.ok(imageUploadFacade.upload(uploadingFiles));
        } catch (IOException exception) {
            log.info(exception.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{imgId}/{Size}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imgId, @PathVariable String Size) {
        File img = imageUploadFacade.getImage(imgId, Size);
        if (img == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            byte[] response = Files.readAllBytes(img.toPath());
            return ResponseEntity.ok().contentType(MediaType.valueOf(MimeTypes.getContentTypeByFileName(img.getName()))).body(response);
        } catch (IOException exception) {
            log.info(exception.toString());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{imgId}")
    public ResponseEntity<String> deleteImage(@PathVariable String imgId) {
        if (imageUploadFacade.delImage(imgId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }
}
