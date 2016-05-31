package cz.tul.controllers;

import cz.tul.client.ServerApi;
import cz.tul.data.Images;
import cz.tul.repositories.BaseAutorRepository;
import cz.tul.repositories.BaseCommentRepository;
import cz.tul.repositories.BaseImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Marek on 31.05.2016.
 */
@RestController
public class ImagesController {
    @Autowired
    BaseImagesRepository baseImagesRepository;

    @RequestMapping(value = ServerApi.IMAGE_PATH, method = RequestMethod.GET)
    public ResponseEntity<Images> getImage(@PathVariable UUID id) {
        Images image = baseImagesRepository.findOne(id);
        if (image == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(image, HttpStatus.OK);
        }
    }

    @RequestMapping(value = ServerApi.IMAGES_PATH, method = RequestMethod.POST)
    public ResponseEntity<Images> saveImage(@RequestBody Images image) {
        if (baseImagesRepository.exists(image.getId())) {
            return new ResponseEntity<>(HttpStatus.FOUND);
        } else {
            baseImagesRepository.save(image);
            return new ResponseEntity<>(image, HttpStatus.OK);
        }
    }

    @RequestMapping(value = ServerApi.IMAGEUPDATE_PATH, method = RequestMethod.PUT)
    public ResponseEntity<Images> updateImage(@RequestBody Images update, @PathVariable UUID id) {
        Images images = baseImagesRepository.findOne(id);
        if (images == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            images.setUpdateDate(new Date(System.currentTimeMillis()));
            images.setName(update.getName());
            images.setDislike(update.getDislike());
            images.setLike(update.getLike());
            images.setUrl(update.getUrl());
            baseImagesRepository.save(images);
            return new ResponseEntity<>(images, HttpStatus.OK);
        }
    }

    @RequestMapping(value = ServerApi.IMAGELIKE_PATH, method = RequestMethod.PUT)
    public ResponseEntity<Images> updateImageAddLike(@PathVariable UUID id) {
        Images images = baseImagesRepository.findOne(id);
        if (images == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            images.setUpdateDate(new Date(System.currentTimeMillis()));
            images.incrementLike();
            baseImagesRepository.save(images);
            return new ResponseEntity<>(images, HttpStatus.OK);
        }
    }

    @RequestMapping(value = ServerApi.IMAGEDISLIKE_PATH, method = RequestMethod.PUT)
    public ResponseEntity<Images> updateImageAddDisLike(@PathVariable UUID id) {
        Images images = baseImagesRepository.findOne(id);
        if (images == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            images.setUpdateDate(new Date(System.currentTimeMillis()));
            images.incrementDisLike();
            baseImagesRepository.save(images);
            return new ResponseEntity<>(images, HttpStatus.OK);
        }
    }

    @RequestMapping(value = ServerApi.IMAGETAG_PATH, method = RequestMethod.PUT)
    public ResponseEntity<Images> updateImageAddTag(@PathVariable UUID id, @PathVariable String tag) {
        Images images = baseImagesRepository.findOne(id);
        if (images == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            images.setUpdateDate(new Date(System.currentTimeMillis()));
            images.addTag(tag);
            baseImagesRepository.save(images);
            return new ResponseEntity<>(images, HttpStatus.OK);
        }
    }

    @RequestMapping(value = ServerApi.IMAGEDDELETE_PATH, method = RequestMethod.DELETE)
    public ResponseEntity deleteImage(@PathVariable UUID id) {
        Images images = baseImagesRepository.findOne(id);
        if (images == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            baseImagesRepository.delete(images);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}