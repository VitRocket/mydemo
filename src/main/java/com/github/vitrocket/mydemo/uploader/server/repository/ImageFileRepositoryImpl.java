package com.github.vitrocket.mydemo.uploader.server.repository;

import com.github.vitrocket.mydemo.uploader.server.model.ImageFile;
import com.mongodb.WriteResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Vit Rocket on 16.09.2017.
 * @version 1.0
 * @since on 16.09.2017
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class ImageFileRepositoryImpl implements ImageFileRepository {

    private final MongoTemplate mongoTemplate;

    public List<ImageFile> getAllImageFiles() {
        return mongoTemplate.findAll(ImageFile.class);
    }

    public String saveImageFile(ImageFile imageFile) {
        mongoTemplate.insert(imageFile);
        return imageFile.getId();
    }

    public ImageFile getImageFile(String id) {
        return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)),
                ImageFile.class);
    }

    public WriteResult updateImageFile(String id, String name) {
        return mongoTemplate.updateFirst(
                new Query(Criteria.where("id").is(id)),
                Update.update("originalName", name), ImageFile.class);
    }

    public void deleteImageFile(String id) {
        mongoTemplate
                .remove(new Query(Criteria.where("id").is(id)), ImageFile.class);
    }
}