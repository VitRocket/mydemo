package com.github.vitrocket.mydemo.uploader.server.controller;

/**
 * @author Vit Rocket on 17.09.2017.
 * @version 1.0
 * @since on 17.09.2017
 */
import org.apache.commons.io.FilenameUtils;
import org.springframework.util.StringUtils;

import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

class MimeTypes {

    private static Map<String, String> fileExtensionMap;

    static {
        fileExtensionMap = new HashMap<>();
        // Media
        fileExtensionMap.put("mp3", "audio/mpeg3");
    }

    public static String getContentTypeByFileName(String fileName) {
        // 1. first use java's buildin utils
        FileNameMap mimeTypes = URLConnection.getFileNameMap();
        String contentType = mimeTypes.getContentTypeFor(fileName);
        // 2. nothing found -> lookup our in extension map to find types like ".mp3"
        if (!StringUtils.hasText(contentType)) {
            String extension = FilenameUtils.getExtension(fileName);
            contentType = fileExtensionMap.get(extension);
        }
        return contentType;
    }
}