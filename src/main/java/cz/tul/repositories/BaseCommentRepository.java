package cz.tul.repositories;


import cz.tul.data.Comment;
import cz.tul.data.Images;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Marek on 28.05.2016.
 */

public interface BaseCommentRepository extends CrudRepository<Comment, String> {
    List<Comment> findByImages(Images images);
}
