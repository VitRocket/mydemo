package com.github.vitrocket.mydemo.uploader.server.repository;

import com.github.vitrocket.mydemo.uploader.server.model.ImageSize;
import com.github.vitrocket.mydemo.uploader.server.model.ImageFile;
import com.github.vitrocket.mydemo.uploader.server.model.ImageFileResize;
import com.mongodb.WriteResult;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vit Rocket on 16.09.2017.
 * @version 1.0
 * @since on 16.09.2017
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ImageFileRepositoryTest extends TestCase {

    @Autowired
    private ImageFileRepository imageFileRepository;

    private String beforeId;

    @Before
    public void testBefore() {
        List<ImageFileResize> fileResizes = new ArrayList<>();
        fileResizes.add(new ImageFileResize("small-file", "small", ImageSize.SMALL));
        fileResizes.add(new ImageFileResize("medium-file", "medium", ImageSize.MEDIUM));
        fileResizes.add(new ImageFileResize("big-file", "big", ImageSize.BIG));
        ImageFile imageFile = ImageFile.builder()
                .originalName("TestFile")
                .loadDateTime(LocalDateTime.now())
                .localUrl("C://Files")
                .imageResizes(fileResizes)
                .build();
        imageFileRepository.saveImageFile(imageFile);
        beforeId = imageFile.getId();
    }

    @After
    public void testAfter() {
        ImageFile imageFile = imageFileRepository.getImageFile(beforeId);
        assertNotNull(imageFile);
        imageFileRepository.deleteImageFile(beforeId);
        imageFile = imageFileRepository.getImageFile(beforeId);
        assertNull(imageFile);
    }


    @Test
    public void testGetAllImageFiles() {
        List<ImageFile> imageFiles = imageFileRepository.getAllImageFiles();
        assertNotNull(imageFiles);
    }

    @Test
    public void testSaveImageFile() {
        ImageFile imageFile = ImageFile.builder()
                .originalName("Hello")
                .loadDateTime(LocalDateTime.now())
                .localUrl("C://Image")
                .build();
        String saveId = imageFileRepository.saveImageFile(imageFile);
        assertNotNull(saveId);
        assertEquals(saveId, imageFile.getId());
        imageFileRepository.deleteImageFile(saveId);
        imageFile = imageFileRepository.getImageFile(saveId);
        assertNull(imageFile);
    }

    @Test
    public void testGetImageFile() {
        ImageFile imageFile = imageFileRepository.getImageFile(beforeId);
        assertNotNull(imageFile);
    }

    @Test
    public void testUpdateImageFile() {
        ImageFile imageFile = imageFileRepository.getImageFile(beforeId);
        assertNotNull(imageFile);
        WriteResult writeResult = imageFileRepository.updateImageFile(imageFile.getId(), "NEW NAME");
        assertEquals(true, writeResult.isUpdateOfExisting());
        imageFile = imageFileRepository.getImageFile(beforeId);
        assertNotNull(imageFile);
        assertEquals("NEW NAME", imageFile.getOriginalName());
    }

    @Test
    public void testDeleteImageFile() {
        ImageFile imageFile = ImageFile.builder()
                .originalName("Hello")
                .loadDateTime(LocalDateTime.now())
                .localUrl("C://Image")
                .build();
        String saveId = imageFileRepository.saveImageFile(imageFile);
        assertNotNull(saveId);
        assertEquals(saveId, imageFile.getId());
        imageFileRepository.deleteImageFile(saveId);
        imageFile = imageFileRepository.getImageFile(saveId);
        assertNull(imageFile);
    }
}