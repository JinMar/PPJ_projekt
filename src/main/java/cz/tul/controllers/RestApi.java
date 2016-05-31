package cz.tul.controllers;

import cz.tul.data.Autor;
import cz.tul.data.Comment;
import cz.tul.data.Images;
import cz.tul.pojo.Box;
import cz.tul.repositories.BaseAutorRepository;
import cz.tul.repositories.BaseCommentRepository;
import cz.tul.repositories.BaseImagesRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Marek on 31.05.2016.
 */
@RestController
@RequestMapping("/rest")
public class RestApi {
    @Autowired
    BaseImagesRepository baseImagesRepository;
    @Autowired
    BaseCommentRepository baseCommentRepository;
    @Autowired
    BaseAutorRepository baseAutorRepository;

    //AUTOR
    @RequestMapping(value = "/getAutor/{id}", method = RequestMethod.GET)
    public Autor getAutor(@PathVariable String id) {
        return baseAutorRepository.findOne(id);
    }

    @RequestMapping(value = "/createAutor", method = RequestMethod.POST)
    public Autor saveAutor(@RequestBody Autor autor) {
        return baseAutorRepository.save(autor);
    }

    @RequestMapping(value = "/updateAutor/{id}", method = RequestMethod.PUT)
    public Autor updateAutor(@RequestBody Autor update, @PathVariable String id) {
        Autor autor = baseAutorRepository.findOne(id);
        autor.setName(update.getName());
        return baseAutorRepository.save(autor);
    }

    @RequestMapping(value = "/deleteAutor/{id}", method = RequestMethod.DELETE)
    public void deleteAutor(@PathVariable String id) {
        Autor autor = baseAutorRepository.findOne(id);
        baseAutorRepository.delete(autor);
    }

    //IMAGE
    @RequestMapping(value = "/getImage/{id}", method = RequestMethod.GET)
    public Images getImage(@PathVariable UUID id) {
        return baseImagesRepository.findOne(id);
    }

    @RequestMapping(value = "/createImage", method = RequestMethod.POST)
    public Images saveImage(@RequestBody Images image) {
        return baseImagesRepository.save(image);
    }

    @RequestMapping(value = "/updateImage/{id}", method = RequestMethod.PUT)
    public Images updateImage(@RequestBody Images update, @PathVariable UUID id) {
        Images images = baseImagesRepository.findOne(id);
        images.setUpdateDate(new Date(System.currentTimeMillis()));
        images.setName(update.getName());
        images.setDislike(update.getDislike());
        images.setLike(update.getLike());
        images.setUrl(update.getUrl());
        return baseImagesRepository.save(images);
    }

    @RequestMapping(value = "/updateImage/addlike/{id}", method = RequestMethod.PUT)
    public Images updateImageAddLike(@RequestBody Images update, @PathVariable UUID id) {
        Images images = baseImagesRepository.findOne(id);
        images.setUpdateDate(new Date(System.currentTimeMillis()));
        images.incrementLike();
        return baseImagesRepository.save(images);
    }

    @RequestMapping(value = "/updateImage/adddislike/{id}", method = RequestMethod.PUT)
    public Images updateImageAddDisLike(@RequestBody Images update, @PathVariable UUID id) {
        Images images = baseImagesRepository.findOne(id);
        images.setUpdateDate(new Date(System.currentTimeMillis()));
        images.incrementDisLike();
        return baseImagesRepository.save(images);
    }

    @RequestMapping(value = "/updateImage/adddislike/{id}/{tag}", method = RequestMethod.PUT)
    public Images updateImageAddTag(@RequestBody Images update, @PathVariable UUID id, @PathVariable String tag) {
        Images images = baseImagesRepository.findOne(id);
        images.setUpdateDate(new Date(System.currentTimeMillis()));
        images.addTag(tag);
        return baseImagesRepository.save(images);
    }

    @RequestMapping(value = "/deleteImage/{id}", method = RequestMethod.DELETE)
    public void deleteImage(@PathVariable UUID id) {
        Images images = baseImagesRepository.findOne(id);
        baseImagesRepository.delete(images);
    }

    //COMMENT

    @RequestMapping(value = "/getComment/{id}", method = RequestMethod.GET)
    public Comment getComment(@PathVariable String id) {
        return baseCommentRepository.findOne(id);
    }

    @RequestMapping(value = "/createComment", method = RequestMethod.POST)
    public Comment saveComment(@RequestBody Comment comment) {
        return baseCommentRepository.save(comment);
    }

    @RequestMapping(value = "/updateCommetn/{id}", method = RequestMethod.PUT)
    public Comment addDisLikecomment(@RequestBody Comment update, @PathVariable String id) {
        Comment comment = baseCommentRepository.findOne(id);
        comment.setDescription(update.getDescription());
        comment.setAutor(update.getAutor());
        comment.setLike(update.getLike());
        comment.setDislike(update.getDislike());
        return baseCommentRepository.save(comment);
    }

    @RequestMapping(value = "/addLikeComment/{id}", method = RequestMethod.PUT)
    public Comment addLikeComment(@PathVariable String id) {
        Comment comment = baseCommentRepository.findOne(id);
        comment.incrementLike();
        return baseCommentRepository.save(comment);
    }

    @RequestMapping(value = "/addDisLikeComment/{id}", method = RequestMethod.PUT)
    public Comment addDisLikeComment(@PathVariable String id) {
        Comment comment = baseCommentRepository.findOne(id);
        comment.incrementDisLike();
        return baseCommentRepository.save(comment);
    }

    @RequestMapping(value = "/deleteComment/{id}", method = RequestMethod.DELETE)
    public void deleteComment(@PathVariable String id) {
        Comment comment = baseCommentRepository.findOne(id);
        baseCommentRepository.delete(comment);
    }


}
