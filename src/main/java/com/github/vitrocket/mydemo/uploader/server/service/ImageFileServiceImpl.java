package com.github.vitrocket.mydemo.uploader.server.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Vit Rocket on 16.09.2017.
 * @version 1.0
 * @since on 16.09.2017
 */
@Slf4j
@Service
public class ImageFileServiceImpl implements ImageFileService {

    private static final String STORAGEIMAGE = System.getProperty("user.dir") + "/storage/image/";

    @Override
    public void saveImageFile(MultipartFile uploadedFile) throws IOException {

        String fileName = uploadedFile.getOriginalFilename();
        String fileExtension = FilenameUtils.getExtension(fileName);

        InputStream in = new ByteArrayInputStream(uploadedFile.getBytes());
        BufferedImage originalImage = ImageIO.read(in);
        String localOriginalUrl = "original/" + fileName;
        ImageIO.write(originalImage, fileExtension, new File(STORAGEIMAGE + localOriginalUrl));

        BufferedImage imageSmall = resizeSmall(uploadedFile.getBytes(), 400, 300);
        String localSmallUrl = "small/" + fileName;
        ImageIO.write(imageSmall, fileExtension, new File(STORAGEIMAGE + localSmallUrl));

        BufferedImage imageMedium = resizeSmall(uploadedFile.getBytes(), 900, 600);
        String localMediumUrl = "medium/" + fileName;
        ImageIO.write(imageMedium, fileExtension, new File(STORAGEIMAGE + localMediumUrl));

        BufferedImage imageBig = resizeSmall(uploadedFile.getBytes(), 1024, 800);
        String localBigUrl = "big/" + fileName;
        ImageIO.write(imageBig, fileExtension, new File(STORAGEIMAGE + localBigUrl));

    }

    private BufferedImage resizeSmall(byte[] bytes, int paramWidth, int paramHeight) throws IOException {
        int resultWidth = paramWidth;
        int resultHeight = paramHeight;

        InputStream in = new ByteArrayInputStream(bytes);
        BufferedImage originalImage = ImageIO.read(in);

        // Get image dimensions
        double width = originalImage.getWidth();
        double height = originalImage.getHeight();
        double widthSmall = resultWidth;
        double heightSmall = resultHeight;

        double coefW = width / widthSmall;
        double coefH = height / heightSmall;
        //log.info("COEF: " + coefW + " X " + coefH);

        int scaledWidth = 0;
        int scaledHeight = 0;

        if (coefW > coefH) {
            scaledHeight = (int) heightSmall;
            scaledWidth = (int) (width / coefH);
        } else if (coefW < coefH) {
            scaledWidth = (int) widthSmall;
            scaledHeight = (int) (height / coefW);
        } else {
            scaledHeight = (int) heightSmall;
            scaledWidth = (int) widthSmall;
        }

        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, originalImage.getType());

        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        int widthDifference = (int) (scaledWidth - widthSmall);
        int heightDifference = (int) (scaledHeight - heightSmall);
        int xc = widthDifference / 2;
        int yc = heightDifference / 2;
        //log.info(scaledWidth + " x " + scaledHeight + " -- " + resultWidth + " x " + resultHeight);
        //log.info(xc + " x " + yc);
        BufferedImage croppedImage = outputImage.getSubimage(
                xc, // x coordinate of the upper-left corner
                yc, // y coordinate of the upper-left corner
                resultWidth,            // widht
                resultHeight             // height
        );

        return croppedImage;
    }
}
