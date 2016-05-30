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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Marek on 28.05.2016.
 */
@RestController

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
            long count =baseImagesRepository.count();
            Images images =baseImagesRepository.findFirstByPositionGreaterThanOrderByPositionAsc(0);
            logger.info("Index loaded "+images.getName() );


            if (count != 0 && images!=null) {

                List<CommentDTO> commentDTOs= new ArrayList<>();

                for (Comment item : baseCommentRepository.findByImages(images)){
                    CommentDTO commentDTO = new CommentDTO();
                    commentDTO.setDislikes(item.getDislike());
                    commentDTO.setLikes(item.getLike());
                    commentDTO.setText(item.getDescription());
                    commentDTO.setId(item.getId());
                    commentDTOs.add(commentDTO);

                }
                Images next = baseImagesRepository.findFirstByPositionGreaterThanOrderByPositionAsc(images.getPosition());
                Images prev = baseImagesRepository.findFirstByPositionLessThanOrderByPositionAsc(images.getPosition());


                String infodata= "Vytvořeno: "+images.getCreateDate()+" Autor: "+images.getAutor().getName()+ " Nazev: "+images.getName();

                view.addObject("tagses", images.getTagses());
                if(next != null){
                view.addObject("next", next.getId());
                    logger.info("Index next "+next.getId() );}
                if(prev !=null){
                    view.addObject("previous", prev.getId() );
                    logger.info("Index prev "+prev.getId() );}
                view.addObject("url", images.getUrl());

                view.addObject("current", images.getId());

                view.addObject("infodata",infodata);
              view.addObject("likes",images.getLike());
                view.addObject("dislikes", images.getDislike());
                if(commentDTOs.size()>0){
                    view.addObject("comemnt", commentDTOs);}


            }view.addObject("mybox",new MyBox());
            logger.info("Index loaded");

            return view;
        }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView next(@PathVariable("id") String id) {
        ModelAndView view = new ModelAndView("index");
        long count =baseImagesRepository.count();
        Images images =baseImagesRepository.findOne(id);




        if (count != 0 && images!=null) {

            List<CommentDTO> commentDTOs= new ArrayList<>();

            for (Comment item : baseCommentRepository.findByImages(images)){
                CommentDTO commentDTO = new CommentDTO();
                commentDTO.setDislikes(item.getDislike());
                commentDTO.setLikes(item.getLike());
                commentDTO.setText(item.getDescription());
                commentDTO.setId(item.getId());
                commentDTOs.add(commentDTO);

            }
            Images next = baseImagesRepository.findFirstByPositionGreaterThanOrderByPositionAsc(images.getPosition());
            Images prev = baseImagesRepository.findFirstByPositionLessThanOrderByPositionAsc(images.getPosition());


            String infodata= "Vytvořeno: "+images.getCreateDate()+" Autor: "+images.getAutor().getName()+ " Nazev: "+images.getName();
            view.addObject("tagses", images.getTagses());

            if(next != null){
                view.addObject("next", next.getId());
                logger.info("Index next "+next.getId() );}
            if(prev !=null){
                view.addObject("previous", prev.getId());
                logger.info("Index prev "+prev.getId() );}
            view.addObject("url", images.getUrl());

            view.addObject("current", images.getId());

            view.addObject("infodata",infodata);
            view.addObject("likes",images.getLike());
            view.addObject("dislikes", images.getDislike());
            if(commentDTOs.size()>0){
                view.addObject("comemnt", commentDTOs);}


        }view.addObject("mybox",new MyBox());
        logger.info("Index loaded");

        return view;
    }

    @RequestMapping(value = "/likeimg/id={id}&next={next}&prv={prev}", method = RequestMethod.GET)
    public ModelAndView addLike(@PathVariable("id")String id,@PathVariable("next") String next,@PathVariable("prev") String prev) {
        ModelAndView view = new ModelAndView("index");

        Images image = baseImagesRepository.findOne(id);
        image.incrementLike();
        long count =baseImagesRepository.count();
        System.out.println(image.getLike() + " *** "+count);
        System.out.println(image.getDislike());

        List<CommentDTO> commentDTOs= new ArrayList<>();

        for (Comment item : baseCommentRepository.findByImages(image)){
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setDislikes(item.getDislike());
            commentDTO.setLikes(item.getLike());
            commentDTO.setText(item.getDescription());
            commentDTO.setId(item.getId());
            commentDTOs.add(commentDTO);

        }


        String infodata= "Vytvořeno: "+image.getCreateDate()+" Autor: "+image.getAutor().getName()+ " Nazev: "+image.getName();
        view.addObject("tagses", image.getTagses());
        view.addObject("next", next);
        view.addObject("url", image.getUrl());
        view.addObject("previous", prev);
        view.addObject("mybox",new MyBox());
        view.addObject("current", id);
        view.addObject("likes",image.getLike());
        view.addObject("dislikes", image.getDislike());
        view.addObject("infodata",infodata);
        if(commentDTOs.size()>0){
            view.addObject("comemnt", commentDTOs);}
        logger.info("Like added");
        baseImagesRepository.save(image);
        return view;
    }
    @RequestMapping(value = "/dislikeimg/id={id}&next={next}&prv={prev}", method = RequestMethod.GET)
    public ModelAndView addDisLike(@PathVariable("id")String id,@PathVariable("next") String next,@PathVariable("prev") String prev) {
        ModelAndView view = new ModelAndView("index");

        Images image = baseImagesRepository.findOne(id);
        image.incrementDisLike();
        long count =baseImagesRepository.count();
        System.out.println(image.getLike() + " *** "+count);
        System.out.println(image.getDislike());

        List<CommentDTO> commentDTOs= new ArrayList<>();

        for (Comment item : baseCommentRepository.findByImages(image)){
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setDislikes(item.getDislike());
            commentDTO.setLikes(item.getLike());
            commentDTO.setText(item.getDescription());
            commentDTO.setId(item.getId());
            commentDTOs.add(commentDTO);

        }


        String infodata= "Vytvořeno: "+image.getCreateDate()+" Autor: "+image.getAutor().getName()+ " Nazev: "+image.getName();
        view.addObject("tagses", image.getTagses());
        view.addObject("next", next);
        view.addObject("url", image.getUrl());
        view.addObject("previous", prev);
        view.addObject("mybox",new MyBox());
        view.addObject("current", id);
        view.addObject("likes",image.getLike());
        view.addObject("dislikes", image.getDislike());
        view.addObject("infodata",infodata);
        if(commentDTOs.size()>0){
            view.addObject("comemnt", commentDTOs);}
        logger.info("Like added");
        baseImagesRepository.save(image);
        return view;
    }
    @RequestMapping(value = "/like/id={id}&com={idc}&next={next}&prv={prev}", method = RequestMethod.GET)
    public ModelAndView addLikeComent(@PathVariable("id")String id,@PathVariable("idc") String idc,@PathVariable("next") String next,@PathVariable("prev") String prev) {
        ModelAndView view = new ModelAndView("index");
        Comment comment = baseCommentRepository.findOne(idc);
        comment.incrementLike();
        baseCommentRepository.save(comment);
        Images image = baseImagesRepository.findOne(id);
        List<CommentDTO> commentDTOs= new ArrayList<>();

        for (Comment item : baseCommentRepository.findByImages(image)){
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setDislikes(item.getDislike());
            commentDTO.setLikes(item.getLike());
            commentDTO.setText(item.getDescription());
            commentDTO.setId(item.getId());
            commentDTOs.add(commentDTO);

        }


        String infodata= "Vytvořeno: "+image.getCreateDate()+" Autor: "+image.getAutor().getName()+ " Nazev: "+image.getName();
        view.addObject("tagses", image.getTagses());
        view.addObject("next", next);
        view.addObject("url", image.getUrl());
        view.addObject("previous", prev);
        view.addObject("mybox",new MyBox());
        view.addObject("current", id);
        view.addObject("likes",image.getLike());
        view.addObject("dislikes", image.getDislike());
        view.addObject("infodata",infodata);
        if(commentDTOs.size()>0){
            view.addObject("comemnt", commentDTOs);}
        logger.info("Like added");
        baseImagesRepository.save(image);
        return view;
    }
    @RequestMapping(value = "/dislike/id={id}&com={idc}&next={next}&prv={prev}", method = RequestMethod.GET)
    public ModelAndView addDisLikeComent(@PathVariable("id")String id,@PathVariable("idc") String idc,@PathVariable("next") String next,@PathVariable("prev") String prev) {
        ModelAndView view = new ModelAndView("index");

        Comment comment = baseCommentRepository.findOne(idc);
        comment.incrementDisLike();
        baseCommentRepository.save(comment);
        Images image = baseImagesRepository.findOne(id);
        List<CommentDTO> commentDTOs= new ArrayList<>();

        for (Comment item : baseCommentRepository.findByImages(image)){
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setDislikes(item.getDislike());
            commentDTO.setLikes(item.getLike());
            commentDTO.setText(item.getDescription());
            commentDTO.setId(item.getId());
            commentDTOs.add(commentDTO);

        }


        String infodata= "Vytvořeno: "+image.getCreateDate()+" Autor: "+image.getAutor().getName()+ " Nazev: "+image.getName();
        view.addObject("tagses", image.getTagses());
        view.addObject("next", next);
        view.addObject("url", image.getUrl());
        view.addObject("previous", prev);
        view.addObject("mybox",new MyBox());
        view.addObject("current", id);
        view.addObject("likes",image.getLike());
        view.addObject("dislikes", image.getDislike());
        view.addObject("infodata",infodata);
        if(commentDTOs.size()>0){
            view.addObject("comemnt", commentDTOs);}
        logger.info("Like added");
        return view;
    }
    @RequestMapping(value = "/saveComment/id={id}/next={next}/prev={prev}", method = RequestMethod.POST)
    public ModelAndView saveComment(@ModelAttribute("mybox") MyBox tb,@PathVariable("id")String id,  @PathVariable("next") String next, @PathVariable("prev") String prev) {
        Images images =baseImagesRepository.findOne(id);
        Comment comment = new Comment(""+ObjectId.get(),tb.getText(), new Date(System.currentTimeMillis()),images,images.getAutor());
        baseCommentRepository.save(comment);

        ModelAndView view = new ModelAndView("index");
        if ( images!=null) {

            List<CommentDTO> commentDTOs= new ArrayList<>();

            for (Comment item : baseCommentRepository.findByImages(images)){
                CommentDTO commentDTO = new CommentDTO();
                commentDTO.setDislikes(item.getDislike());
                commentDTO.setLikes(item.getLike());
                commentDTO.setText(item.getDescription());
                commentDTO.setId(item.getId());
                commentDTOs.add(commentDTO);

            }


            String infodata= "Vytvořeno: "+images.getCreateDate()+" Autor: "+images.getAutor().getName()+ " Nazev: "+images.getName();
            view.addObject("tagses", images.getTagses());

                view.addObject("next", next);
                logger.info("Index next "+next );

                view.addObject("previous", prev);
                logger.info("Index prev "+prev );
            view.addObject("url", images.getUrl());

            view.addObject("current", images.getId());

            view.addObject("infodata",infodata);
            view.addObject("likes",images.getLike());
            view.addObject("dislikes", images.getDislike());
            if(commentDTOs.size()>0){
                view.addObject("comemnt", commentDTOs);}
        logger.info("Comment saved");

    }return view;}
    @RequestMapping(value = "/saveAutor", method = RequestMethod.POST)
    public
    @ResponseBody
    List<Box> saveAutors(@RequestBody final List<Box> box) {
        List<Box> boxes = new ArrayList<>();

        for (Box item : box) {
            if (item.getName().length() > 0) {Box box1 =new Box();
                Autor insert = new Autor(""+ObjectId.get(),item.getName(), new Date(System.currentTimeMillis()));
                baseAutorRepository.save(insert);
                box1.setName(insert.getName());
                box1.setAutorId(insert.getId());
                System.out.println("Autor saved: " + item.getName());
                boxes.add(box1);
            }

        }
        logger.info("saveAutor done !");
        return boxes;
    }
    @RequestMapping(value = "/saveImage", method = RequestMethod.POST)
    public
    @ResponseBody
    List<Box> saveImages(@RequestBody final List<Box> box) {
        List<Box> boxes = new ArrayList<>();

        for (Box item : box) {
            if (item.getName().length() > 0) {
                Autor autor = baseAutorRepository.findOne(item.getAutorId());
                if (autor != null) {
                    Box box1 =new Box();
                    box1.setName(item.getName());
                    box1.setAutorId(autor.getId());
                    int max = baseImagesRepository.findFirstByPositionGreaterThanOrderByPositionDesc(0).getPosition()+1;
                    Images insert = new Images( ""+ObjectId.get(),item.getUrl(),item.getName(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), autor,max);
                    baseImagesRepository.save(insert);
                    box1.setImageId(insert.getId());
                    System.out.println("Image saved: " + item.getName());
                    boxes.add(box1);
                }
            }

        }

        logger.info("saveImage done !");
        return boxes;
    }







    @RequestMapping(value = "/saveComment", method = RequestMethod.POST)
    public
    @ResponseBody
    List<Box> saveComment(@RequestBody final List<Box> box) {
        List<Box> boxes = new ArrayList<>();
        logger.info("Start saving comment");
        for (Box item : box) {
            Images images = baseImagesRepository.findOne(item.getImageId());
            Autor autor = baseAutorRepository.findOne(item.getAutorId());
            if (images != null && autor!=null) {

                Box box1 =new Box();
                Comment insert = new Comment(""+ObjectId.get(),item.getDescription(), new Date(System.currentTimeMillis()),images,autor);
                baseCommentRepository.save(insert);
                box1.setImageId(images.getId());
                box1.setAutorId(autor.getId());
                box1.setCommentId(insert.getId());
                boxes.add(box1);
                logger.info("AutorId: "+autor.getId()+ " imageId: " +images.getId() +" text: "+insert.getDescription());
            }

            logger.info("saveComment done !");
    }
        return boxes;
}
    @RequestMapping(value = "/saveCommentLike", method = RequestMethod.POST)
    public
    @ResponseBody
    List<Box> saveCommentLike(@RequestBody final List<Box> box) {
        List<Box> boxes = new ArrayList<>();


        logger.info("saveCommentLike done !");
        return boxes;
    }
    @RequestMapping(value = "/img/{name}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Box> imgByName(@PathVariable("name") String name) {
        List<Box> boxes = new ArrayList<>();

        for(Images item : baseImagesRepository.findByName(name)){
            Box box =new Box();
            box.setAutorId(item.getAutor().getId());
            box.setImageId(item.getId());
            box.setName(item.getName());
            box.setUrl(item.getUrl());
            boxes.add(box);

        }
        logger.info("img done !");
        return boxes;
    }
    @RequestMapping(value = "/imgt/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Box> imgBytag(@PathVariable("id") String id) {
        List<Box> boxes = new ArrayList<>();
        Images image = baseImagesRepository.findOne(id);

            Box box =new Box();
            box.setAutorId(image.getAutor().getId());
            box.setImageId(id);
            box.setName(image.getTagses());
            box.setUrl(image.getUrl());

            boxes.add(box);


        logger.info("imgt done !");
        return boxes;
    }
    @RequestMapping(value = "/imga/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Box> imgByAutor(@PathVariable("id") String id) {

        List<Box> boxes = new ArrayList<>();
        Autor autor = baseAutorRepository.findOne(id);
        for(Images item : baseImagesRepository.findByAutor(autor)){
            Box box =new Box();
            box.setAutorId(item.getAutor().getId());
            box.setImageId(item.getId());
            box.setName(item.getName());
            box.setUrl(item.getUrl());
            boxes.add(box);

        }
        logger.info("imga done !");
        return boxes;
    }

    @RequestMapping(value = "/updateimgname", method = RequestMethod.POST)
    public
    @ResponseBody
    List<Box> updateName(@RequestBody final List<Box> box) {

        List<Box> boxes = new ArrayList<>();
        Images item = baseImagesRepository.findOne(box.get(0).getImageId());
        if(item !=null) {
            item.setName(box.get(0).getName());
            baseImagesRepository.save(item);
            Box box1 = new Box();
            box1.setAutorId(item.getAutor().getId());
            box1.setImageId(item.getId());
            box1.setName(item.getName());
            box1.setUrl(item.getUrl());
            boxes.add(box1);
        }

        logger.info("update done !");
        return boxes;
    }
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Box> deleteImage(@PathVariable("id") String id) {

        List<Box> boxes = new ArrayList<>();
        baseImagesRepository.delete(baseImagesRepository.findOne(id));
        logger.info("delete done !");
        return boxes;
    }

}
