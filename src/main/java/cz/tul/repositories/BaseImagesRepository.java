package cz.tul.repositories;

import cz.tul.data.Autor;
import cz.tul.data.Images;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Marek on 28.05.2016.
 */

public interface BaseImagesRepository extends CrudRepository<Images, UUID> {


    List<Images> findByAutor(Autor autor);

    List<Images> findByName(String name);

    Long countByAutor(Autor autor);

    Images findFirstByCreatedateAfterOrderByCreatedateAsc(Date date);


    Images findFirstByCreatedateBeforeOrderByCreatedateDesc(Date date);

    Images findByTagsLike(String tag);


}
