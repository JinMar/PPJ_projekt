package cz.tul.repositories;

import cz.tul.data.Autor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

/**
 * Created by Marek on 28.05.2016.
 */

public interface BaseAutorRepository extends CrudRepository<Autor, UUID> {

    Autor findByName(String naem);

}
