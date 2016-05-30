package cz.tul.repositories;

import cz.tul.data.Autor;
import cz.tul.data.Images;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Marek on 28.05.2016.
 */

public interface BaseImagesRepository extends CrudRepository<Images, String> {


    List<Images> findByAutor(Autor autor);
    List<Images> findByName(String name);
    Long countByAutor(Autor autor);
    Images findFirstByPositionGreaterThanOrderByPositionAsc(int position);
    Images findFirstByPositionLessThanOrderByPositionAsc(int position);
    Images findFirstByPositionGreaterThanOrderByPositionDesc(int position);
    Images findByTagsesLike(String tag);



}
