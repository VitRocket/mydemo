package com.github.vitrocket.mydemo.uploader.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Vit Rocket on 16.09.2017.
 * @version 1.0
 * @since on 16.09.2017
 */
@Data
@AllArgsConstructor
public class ImageFileResize {

    private String name;
    private String localUrl;
    private ImageSize imageSize;

}
