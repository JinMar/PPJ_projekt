package cz.tul.controllers;

import cz.tul.client.ServerApi;
import cz.tul.data.Comment;
import cz.tul.repositories.BaseAutorRepository;
import cz.tul.repositories.BaseCommentRepository;
import cz.tul.repositories.BaseImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Created by Marek on 31.05.2016.
 */
@RestController
public class CommentController {

    @Autowired
    BaseCommentRepository baseCommentRepository;


    @RequestMapping(value = ServerApi.COMMENT_PATH, method = RequestMethod.GET)
    public ResponseEntity<Comment> getComment(@PathVariable UUID id) {
        Comment comment = baseCommentRepository.findOne(id);
        if (comment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(comment, HttpStatus.OK);
        }
    }

    @RequestMapping(value = ServerApi.COMMENTS_PATH, method = RequestMethod.POST)
    public  ResponseEntity<Comment> saveComment(@RequestBody Comment comment) {
        if (baseCommentRepository.exists(comment.getId())) {
            return new ResponseEntity<>(HttpStatus.FOUND);
        } else {
            baseCommentRepository.save(comment);
            return new ResponseEntity<>(comment, HttpStatus.OK);
        }

    }

    @RequestMapping(value = ServerApi.COMMENTUPDATE_PATH, method = RequestMethod.PUT)
    public ResponseEntity<Comment> updateComment(@RequestBody Comment update, @PathVariable UUID id) {
        Comment comment = baseCommentRepository.findOne(id);
        if (comment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            comment.setDescription(update.getDescription());
            comment.setAutor(update.getAutor());
            comment.setLike(update.getLike());
            comment.setDislike(update.getDislike());
            baseCommentRepository.save(comment);
            return new ResponseEntity<>(comment, HttpStatus.OK);
        }
    }

    @RequestMapping(value = ServerApi.COMMENTLIKE_PATH, method = RequestMethod.PUT)
    public ResponseEntity<Comment> addLikeComment(@PathVariable UUID id) {
        Comment comment = baseCommentRepository.findOne(id);
        if (comment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            comment.incrementLike();
            baseCommentRepository.save(comment);
            return new ResponseEntity<>(comment, HttpStatus.OK);
        }
    }

    @RequestMapping(value = ServerApi.COMMENTDISLIKE_PATH, method = RequestMethod.PUT)
    public ResponseEntity<Comment> addDisLikeComment(@PathVariable UUID id) {
        Comment comment = baseCommentRepository.findOne(id);
        if (comment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            comment.incrementDisLike();
            baseCommentRepository.save(comment);
            return new ResponseEntity<>(comment, HttpStatus.OK);
        }
    }

    @RequestMapping(value = ServerApi.COMMENTDELETE_PATH, method = RequestMethod.DELETE)
    public ResponseEntity deleteComment(@PathVariable UUID id) {
        Comment comment = baseCommentRepository.findOne(id);
        if (comment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            baseCommentRepository.delete(comment);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
