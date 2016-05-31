package cz.tul.controllers;


import cz.tul.PpjApplication;
import cz.tul.data.Autor;
import cz.tul.data.Comment;
import cz.tul.data.Images;
import cz.tul.pojo.Box;
import cz.tul.pojo.CommentDTO;
import cz.tul.pojo.MyBox;
import cz.tul.repositories.BaseAutorRepository;
import cz.tul.repositories.BaseCommentRepository;
import cz.tul.repositories.BaseImagesRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Marek on 28.05.2016.
 */
@Controller

@RequestMapping("/")
public class BasicController {
    @Autowired
    BaseImagesRepository baseImagesRepository;
    @Autowired
    BaseCommentRepository baseCommentRepository;
    @Autowired
    BaseAutorRepository baseAutorRepository;


    final static Logger logger = LoggerFactory.getLogger(PpjApplication.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("index");
        long count = baseImagesRepository.count();
        Images images = baseImagesRepository.findFirstByCreatedateAfterOrderByCreatedateAsc(new Date(0));
        logger.info("Index loaded " + images.getName());


        if (count != 0 && images != null) {

            List<CommentDTO> commentDTOs = new ArrayList<>();

            for (Comment item : baseCommentRepository.findByImages(images)) {
                CommentDTO commentDTO = new CommentDTO();
                commentDTO.setDislikes(item.getDislike());
                commentDTO.setLikes(item.getLike());
                commentDTO.setText(item.getDescription());
                commentDTO.setId(item.getId());
                commentDTOs.add(commentDTO);

            }
            Images next = baseImagesRepository.findFirstByCreatedateAfterOrderByCreatedateAsc(images.getCreatedate());
            Images prev = baseImagesRepository.findFirstByCreatedateBeforeOrderByCreatedateDesc(images.getCreatedate());


            String infodata = "Vytvořeno: " + images.getCreatedate() + " Autor: " + images.getAutor().getName() + " Nazev: " + images.getName();

            view.addObject("tagses", images.getTags());
            if (next != null) {
                view.addObject("next", next.getId());
                logger.info("Index next " + next.getId());
            }
            if (prev != null) {
                view.addObject("previous", prev.getId());
                logger.info("Index prev " + prev.getId());
            }
            view.addObject("url", images.getUrl());

            view.addObject("current", images.getId());

            view.addObject("infodata", infodata);
            view.addObject("likes", images.getLike());
            view.addObject("dislikes", images.getDislike());
            if (commentDTOs.size() > 0) {
                view.addObject("comemnt", commentDTOs);
            }


        }
        view.addObject("mybox", new MyBox());
        logger.info("Index loaded");

        return view;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView next(@PathVariable("id") UUID id) {
        ModelAndView view = new ModelAndView("index");
        long count = baseImagesRepository.count();
        Images images = baseImagesRepository.findOne(id);


        if (count != 0 && images != null) {

            List<CommentDTO> commentDTOs = new ArrayList<>();

            for (Comment item : baseCommentRepository.findByImages(images)) {
                CommentDTO commentDTO = new CommentDTO();
                commentDTO.setDislikes(item.getDislike());
                commentDTO.setLikes(item.getLike());
                commentDTO.setText(item.getDescription());
                commentDTO.setId(item.getId());
                commentDTOs.add(commentDTO);

            }
            Images next = baseImagesRepository.findFirstByCreatedateAfterOrderByCreatedateAsc(images.getCreatedate());
            Images prev = baseImagesRepository.findFirstByCreatedateBeforeOrderByCreatedateDesc(images.getCreatedate());

            String infodata = "Vytvořeno: " + images.getCreatedate() + " Autor: " + images.getAutor().getName() + " Nazev: " + images.getName();
            view.addObject("tagses", images.getTags());

            if (next != null) {
                view.addObject("next", next.getId());
                logger.info("Index next " + next.getId());
            }
            if (prev != null) {
                view.addObject("previous", prev.getId());
                logger.info("Index prev " + prev.getId());
            }
            view.addObject("url", images.getUrl());

            view.addObject("current", images.getId());

            view.addObject("infodata", infodata);
            view.addObject("likes", images.getLike());
            view.addObject("dislikes", images.getDislike());
            if (commentDTOs.size() > 0) {
                view.addObject("comemnt", commentDTOs);
            }


        }
        view.addObject("mybox", new MyBox());
        logger.info("Index loaded");

        return view;
    }

    @RequestMapping(value = "/likeimg/id={id}&next={next}&prv={prev}", method = RequestMethod.GET)
    public ModelAndView addLike(@PathVariable("id") UUID id, @PathVariable("next") String next, @PathVariable("prev") String prev) {
        ModelAndView view = new ModelAndView("index");

        Images image = baseImagesRepository.findOne(id);
        image.incrementLike();
        long count = baseImagesRepository.count();


        List<CommentDTO> commentDTOs = new ArrayList<>();

        for (Comment item : baseCommentRepository.findByImages(image)) {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setDislikes(item.getDislike());
            commentDTO.setLikes(item.getLike());
            commentDTO.setText(item.getDescription());
            commentDTO.setId(item.getId());
            commentDTOs.add(commentDTO);

        }


        String infodata = "Vytvořeno: " + image.getCreatedate() + " Autor: " + image.getAutor().getName() + " Nazev: " + image.getName();
        view.addObject("tagses", image.getTags());
        view.addObject("next", next);
        view.addObject("url", image.getUrl());
        view.addObject("previous", prev);
        view.addObject("mybox", new MyBox());
        view.addObject("current", id);
        view.addObject("likes", image.getLike());
        view.addObject("dislikes", image.getDislike());
        view.addObject("infodata", infodata);
        if (commentDTOs.size() > 0) {
            view.addObject("comemnt", commentDTOs);
        }
        logger.info("Like added");
        baseImagesRepository.save(image);
        return view;
    }

    @RequestMapping(value = "/dislikeimg/id={id}&next={next}&prv={prev}", method = RequestMethod.GET)
    public ModelAndView addDisLike(@PathVariable("id") UUID id, @PathVariable("next") String next, @PathVariable("prev") String prev) {
        ModelAndView view = new ModelAndView("index");

        Images image = baseImagesRepository.findOne(id);
        image.incrementDisLike();
        long count = baseImagesRepository.count();


        List<CommentDTO> commentDTOs = new ArrayList<>();

        for (Comment item : baseCommentRepository.findByImages(image)) {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setDislikes(item.getDislike());
            commentDTO.setLikes(item.getLike());
            commentDTO.setText(item.getDescription());
            commentDTO.setId(item.getId());
            commentDTOs.add(commentDTO);

        }


        String infodata = "Vytvořeno: " + image.getCreatedate() + " Autor: " + image.getAutor().getName() + " Nazev: " + image.getName();
        view.addObject("tagses", image.getTags());
        view.addObject("next", next);
        view.addObject("url", image.getUrl());
        view.addObject("previous", prev);
        view.addObject("mybox", new MyBox());
        view.addObject("current", id);
        view.addObject("likes", image.getLike());
        view.addObject("dislikes", image.getDislike());
        view.addObject("infodata", infodata);
        if (commentDTOs.size() > 0) {
            view.addObject("comemnt", commentDTOs);
        }
        logger.info("Like added");
        baseImagesRepository.save(image);
        return view;
    }

    @RequestMapping(value = "/like/id={id}&com={idc}&next={next}&prv={prev}", method = RequestMethod.GET)
    public ModelAndView addLikeComent(@PathVariable("id") UUID id, @PathVariable("idc") String idc, @PathVariable("next") String next, @PathVariable("prev") String prev) {
        ModelAndView view = new ModelAndView("index");
        Comment comment = baseCommentRepository.findOne(idc);
        comment.incrementLike();
        baseCommentRepository.save(comment);
        Images image = baseImagesRepository.findOne(id);
        List<CommentDTO> commentDTOs = new ArrayList<>();

        for (Comment item : baseCommentRepository.findByImages(image)) {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setDislikes(item.getDislike());
            commentDTO.setLikes(item.getLike());
            commentDTO.setText(item.getDescription());
            commentDTO.setId(item.getId());
            commentDTOs.add(commentDTO);

        }


        String infodata = "Vytvořeno: " + image.getCreatedate() + " Autor: " + image.getAutor().getName() + " Nazev: " + image.getName();
        view.addObject("tagses", image.getTags());
        view.addObject("next", next);
        view.addObject("url", image.getUrl());
        view.addObject("previous", prev);
        view.addObject("mybox", new MyBox());
        view.addObject("current", id);
        view.addObject("likes", image.getLike());
        view.addObject("dislikes", image.getDislike());
        view.addObject("infodata", infodata);
        if (commentDTOs.size() > 0) {
            view.addObject("comemnt", commentDTOs);
        }
        logger.info("Like added");
        baseImagesRepository.save(image);
        return view;
    }

    @RequestMapping(value = "/dislike/id={id}&com={idc}&next={next}&prv={prev}", method = RequestMethod.GET)
    public ModelAndView addDisLikeComent(@PathVariable("id") UUID id, @PathVariable("idc") String idc, @PathVariable("next") String next, @PathVariable("prev") String prev) {
        ModelAndView view = new ModelAndView("index");

        Comment comment = baseCommentRepository.findOne(idc);
        comment.incrementDisLike();
        baseCommentRepository.save(comment);
        Images image = baseImagesRepository.findOne(id);
        List<CommentDTO> commentDTOs = new ArrayList<>();

        for (Comment item : baseCommentRepository.findByImages(image)) {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setDislikes(item.getDislike());
            commentDTO.setLikes(item.getLike());
            commentDTO.setText(item.getDescription());
            commentDTO.setId(item.getId());
            commentDTOs.add(commentDTO);

        }


        String infodata = "Vytvořeno: " + image.getCreatedate() + " Autor: " + image.getAutor().getName() + " Nazev: " + image.getName();
        view.addObject("tagses", image.getTags());
        view.addObject("next", next);
        view.addObject("url", image.getUrl());
        view.addObject("previous", prev);
        view.addObject("mybox", new MyBox());
        view.addObject("current", id);
        view.addObject("likes", image.getLike());
        view.addObject("dislikes", image.getDislike());
        view.addObject("infodata", infodata);
        if (commentDTOs.size() > 0) {
            view.addObject("comemnt", commentDTOs);
        }
        logger.info("Like added");
        return view;
    }

    @RequestMapping(value = "/saveComment/id={id}/next={next}/prev={prev}", method = RequestMethod.POST)
    public ModelAndView saveComment(@ModelAttribute("mybox") MyBox tb, @PathVariable("id") UUID id, @PathVariable("next") String next, @PathVariable("prev") String prev) {
        Images images = baseImagesRepository.findOne(id);
        Comment comment = new Comment("" + ObjectId.get(), tb.getText(), new Date(System.currentTimeMillis()), images, images.getAutor());
        baseCommentRepository.save(comment);

        ModelAndView view = new ModelAndView("index");
        if (images != null) {

            List<CommentDTO> commentDTOs = new ArrayList<>();

            for (Comment item : baseCommentRepository.findByImages(images)) {
                CommentDTO commentDTO = new CommentDTO();
                commentDTO.setDislikes(item.getDislike());
                commentDTO.setLikes(item.getLike());
                commentDTO.setText(item.getDescription());
                commentDTO.setId(item.getId());
                commentDTOs.add(commentDTO);

            }


            String infodata = "Vytvořeno: " + images.getCreatedate() + " Autor: " + images.getAutor().getName() + " Nazev: " + images.getName();
            view.addObject("tagses", images.getTags());

            view.addObject("next", next);
            logger.info("Index next " + next);

            view.addObject("previous", prev);
            logger.info("Index prev " + prev);
            view.addObject("url", images.getUrl());

            view.addObject("current", images.getId());

            view.addObject("infodata", infodata);
            view.addObject("likes", images.getLike());
            view.addObject("dislikes", images.getDislike());
            if (commentDTOs.size() > 0) {
                view.addObject("comemnt", commentDTOs);
            }
            logger.info("Comment saved");

        }
        return view;
    }


}
