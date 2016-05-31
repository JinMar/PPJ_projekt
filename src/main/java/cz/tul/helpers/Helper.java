package cz.tul.helpers;

import cz.tul.PpjApplication;
import cz.tul.data.Comment;
import cz.tul.data.Images;
import cz.tul.pojo.Box;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

/**
 * Created by Marek on 31.05.2016.
 */
public  class Helper {
    final static Logger logger = LoggerFactory.getLogger(PpjApplication.class);
    public static ModelAndView modelViewCreator(ModelAndView view, Images images,
                                                UUID next, UUID prev, List<Comment> items){


            String infodata = "Vytvořeno: " + images.getCreatedate() + " Autor: " + images.getAutor().getName() + " Nazev: " + images.getName();
            view.addObject("tagses", images.getTags());
            if (next != null) {
                view.addObject("next", next);
                logger.info("Index next " + next);
            }
            if (prev != null) {
                view.addObject("previous", prev);
                logger.info("Index prev " + prev);
            }
            view.addObject("url", images.getUrl());
            view.addObject("current", images.getId());
            view.addObject("infodata", infodata);
            view.addObject("likes", images.getLike());
            view.addObject("dislikes", images.getDislike());
            if (items.size() > 0) {
                view.addObject("comemnt", items);
            }
        view.addObject("box", new Box());
        return view;
    }
}
