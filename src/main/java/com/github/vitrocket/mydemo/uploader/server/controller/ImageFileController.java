package com.github.vitrocket.mydemo.uploader.server.controller;

import com.github.vitrocket.mydemo.uploader.server.facade.ImageUploadFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
    public ResponseEntity<List<String>> uploadingPost(@RequestParam("uploadingFiles") MultipartFile[] uploadingFiles) throws IOException {
        List<String> response = imageUploadFacade.upload(uploadingFiles);
        //log.info(response.toString());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{imgId}/{Size}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imgId, @PathVariable String Size) throws IOException {
        File img = imageUploadFacade.getImage(imgId, Size);
        //log.info(MimeTypes.getContentTypeByFileName(img.getName()));
        return ResponseEntity.ok().contentType(MediaType.valueOf(MimeTypes.getContentTypeByFileName(img.getName()))).body(Files.readAllBytes(img.toPath()));
    }
}
