package cz.tul.controllers;

import cz.tul.client.ServerApi;
import cz.tul.data.Autor;
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
public class AutorController {
    @Autowired
    BaseAutorRepository baseAutorRepository;

    @RequestMapping(value = ServerApi.AUTOR_PATH, method = RequestMethod.GET)
    public ResponseEntity<Autor> getAutor(@PathVariable UUID id) {
        Autor autor = baseAutorRepository.findOne(id);
        if (autor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(autor, HttpStatus.OK);
        }
    }

    @RequestMapping(value = ServerApi.AUTORS_PATH, method = RequestMethod.POST)
    public ResponseEntity<Autor> saveAutor(@RequestBody Autor autor) {
        if (baseAutorRepository.exists(autor.getId())) {
            return new ResponseEntity<>(HttpStatus.FOUND);
        } else {
            baseAutorRepository.save(autor);
            return new ResponseEntity<>(autor, HttpStatus.OK);
        }
    }

    @RequestMapping(value = ServerApi.AUTORUPDATE_PATH, method = RequestMethod.PUT)
    public ResponseEntity<Autor> updateAutor(@RequestBody Autor autor, @PathVariable UUID id) {
        if (!baseAutorRepository.exists(autor.getId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Autor update = baseAutorRepository.findOne(id);
            update.setName(update.getName());
            baseAutorRepository.save(update);
            return new ResponseEntity<>(update, HttpStatus.OK);
        }
    }

    @RequestMapping(value = ServerApi.AUTORDELETEPATH, method = RequestMethod.DELETE)
    public ResponseEntity deleteAutor(@PathVariable UUID id) {
        Autor autor = baseAutorRepository.findOne(id);
        if (autor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            baseAutorRepository.delete(autor);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
