package com.github.vitrocket.mydemo.uploader.server.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vit Rocket on 16.09.2017.
 * @version 1.0
 * @since on 16.09.2017
 */
@Document
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class ImageFile {

    @Id
    private String id;
    private String originalName;
    private LocalDateTime loadDateTime;
    private String localUrl;
    private String format;
    @Setter
    private List<ImageFileResize> imageResizes = new ArrayList<>();

    @Builder
    public ImageFile(String id, String originalName, LocalDateTime loadDateTime, String localUrl, String format, List<ImageFileResize> imageResizes) {
        this.id = id;
        this.originalName = originalName;
        this.loadDateTime = loadDateTime;
        this.localUrl = localUrl;
        this.format = format;
        if (imageResizes != null) {
            this.imageResizes.addAll(imageResizes);
        }
    }


}
