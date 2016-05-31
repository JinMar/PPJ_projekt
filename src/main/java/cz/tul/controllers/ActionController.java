package cz.tul.controllers;


import cz.tul.PpjApplication;
import cz.tul.data.Comment;
import cz.tul.data.Images;
import cz.tul.helpers.Helper;
import cz.tul.pojo.Box;
import cz.tul.repositories.BaseAutorRepository;
import cz.tul.repositories.BaseCommentRepository;
import cz.tul.repositories.BaseImagesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Marek on 28.05.2016.
 */
@Controller
@RequestMapping("/")
public class ActionController {
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


        if (count != 0 && images != null) {
            Images next = baseImagesRepository.findFirstByCreatedateAfterOrderByCreatedateAsc(images.getCreatedate());
            Images prev = baseImagesRepository.findFirstByCreatedateBeforeOrderByCreatedateDesc(images.getCreatedate());
            if (next != null && prev != null) {
                view = Helper.modelViewCreator(view, images, next.getId(), prev.getId(), baseCommentRepository.findByImages(images));
            }
            if (next == null && prev != null) {
                view = Helper.modelViewCreator(view, images, null, prev.getId(), baseCommentRepository.findByImages(images));
            }
            if (next != null && prev == null) {
                view = Helper.modelViewCreator(view, images, next.getId(), null, baseCommentRepository.findByImages(images));
            }

        }
        logger.info("Index loaded");

        return view;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView next(@PathVariable("id") UUID id) {
        ModelAndView view = new ModelAndView("index");
        long count = baseImagesRepository.count();
        Images images = baseImagesRepository.findOne(id);

        if (count != 0 && images != null) {
            Images next = baseImagesRepository.findFirstByCreatedateAfterOrderByCreatedateAsc(images.getCreatedate());
            Images prev = baseImagesRepository.findFirstByCreatedateBeforeOrderByCreatedateDesc(images.getCreatedate());
            if (next != null && prev != null) {
                view = Helper.modelViewCreator(view, images, next.getId(), prev.getId(), baseCommentRepository.findByImages(images));
            } else if (next == null && prev != null) {
                view = Helper.modelViewCreator(view, images, null, prev.getId(), baseCommentRepository.findByImages(images));
            } else if (next != null && prev == null) {
                view = Helper.modelViewCreator(view, images, next.getId(), null, baseCommentRepository.findByImages(images));
            }
        }
        logger.info("Index loaded");

        return view;
    }

    @RequestMapping(value = "/likeimg/id={id}&next={next}&prv={prev}", method = RequestMethod.GET)
    public ModelAndView addLike(@PathVariable("id") UUID id, @PathVariable("next") UUID next, @PathVariable("prev") UUID prev) {
        ModelAndView view = new ModelAndView("index");

        Images images = baseImagesRepository.findOne(id);
        images.incrementLike();
        baseImagesRepository.save(images);
        long count = baseImagesRepository.count();
        if (count != 0 && images != null) {
            view = Helper.modelViewCreator(view, images, next, prev, baseCommentRepository.findByImages(images));
        }
        logger.info("Like added");

        return view;
    }

    @RequestMapping(value = "/dislikeimg/id={id}&next={next}&prv={prev}", method = RequestMethod.GET)
    public ModelAndView addDisLike(@PathVariable("id") UUID id, @PathVariable("next") UUID next, @PathVariable("prev") UUID prev) {
        ModelAndView view = new ModelAndView("index");

        Images images = baseImagesRepository.findOne(id);
        images.incrementDisLike();
        baseImagesRepository.save(images);
        long count = baseImagesRepository.count();
        if (count != 0 && images != null) {
            view = Helper.modelViewCreator(view, images, next, prev, baseCommentRepository.findByImages(images));
        }

        return view;
    }

    @RequestMapping(value = "/like/id={id}&com={idc}&next={next}&prv={prev}", method = RequestMethod.GET)
    public ModelAndView addLikeComent(@PathVariable("id") UUID id, @PathVariable("idc") UUID idc, @PathVariable("next") UUID next, @PathVariable("prev") UUID prev) {
        ModelAndView view = new ModelAndView("index");
        Comment comment = baseCommentRepository.findOne(idc);
        comment.incrementLike();
        baseCommentRepository.save(comment);
        Images images = baseImagesRepository.findOne(id);
        long count = baseImagesRepository.count();
        if (count != 0 && images != null) {
            view = Helper.modelViewCreator(view, images, next, prev, baseCommentRepository.findByImages(images));
        }

        return view;
    }

    @RequestMapping(value = "/dislike/id={id}&com={idc}&next={next}&prv={prev}", method = RequestMethod.GET)
    public ModelAndView addDisLikeComent(@PathVariable("id") UUID id, @PathVariable("idc") UUID idc, @PathVariable("next") UUID next, @PathVariable("prev") UUID prev) {
        ModelAndView view = new ModelAndView("index");
        Comment comment = baseCommentRepository.findOne(idc);
        comment.incrementDisLike();
        baseCommentRepository.save(comment);
        Images images = baseImagesRepository.findOne(id);
        long count = baseImagesRepository.count();
        if (count != 0 && images != null) {
            view = Helper.modelViewCreator(view, images, next, prev, baseCommentRepository.findByImages(images));
        }
        logger.info("Like added");
        return view;
    }

    @RequestMapping(value = "/saveComment/id={id}/next={next}/prev={prev}", method = RequestMethod.POST)
    public ModelAndView saveComment(@ModelAttribute("box") Box tb, @PathVariable("id") UUID id, @PathVariable("next") UUID next, @PathVariable("prev") UUID prev) {
        ModelAndView view = new ModelAndView("index");
        Images images = baseImagesRepository.findOne(id);
        Comment comment = new Comment(UUID.randomUUID(), tb.getDescription(), new Date(System.currentTimeMillis()), images, images.getAutor());
        baseCommentRepository.save(comment);
        long count = baseImagesRepository.count();
        if (count != 0 && images != null) {
            view = Helper.modelViewCreator(view, images, next, prev, baseCommentRepository.findByImages(images));
        }
        logger.info("Comment saved");


        return view;
    }


}
