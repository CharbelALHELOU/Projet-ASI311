package com.ensta.myfilmlist.controller;

import java.util.List;

import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.dto.PageDTO;
import com.ensta.myfilmlist.exception.ControllerException;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.service.FilmService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import io.swagger.annotations.ApiOperation;

@RestController("FilmWS")
@RequestMapping(value = "/film")
@CrossOrigin(origins = "http://localhost:4200")
public class FilmWS {

    private FilmService filmService;

    public FilmWS(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    @ApiOperation(value = "Get list of films")
    public ResponseEntity<PageDTO<FilmDTO>> retrieveFilm(@RequestParam int number, @RequestParam int size, @RequestParam String order, @RequestParam boolean sens) throws ControllerException {
        try {
            long total = filmService.getTotal(size);
            if (number>total) {
                throw new ServiceException("Please choose page number lower than total number of pages " );
            }
            List<FilmDTO> films = filmService.findAll(number,size,order,sens);
            PageDTO<FilmDTO> page = new PageDTO<FilmDTO>(number,size,total,films);
            return ResponseEntity.status(HttpStatus.OK).body(page);
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage());
        }
    }

    @GetMapping("{id}")
    @ApiOperation(value = "Get film by id")
    public ResponseEntity<FilmDTO> retrieveFilmById(@RequestParam String id) throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(filmService.findById(id));
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage());
        }
    }

    @GetMapping("{titre}/{director}")
    @ApiOperation(value = "get films with")
    public ResponseEntity<PageDTO<FilmDTO>> retrieveFilmsWithFilter(@RequestParam String titre, @RequestParam String director, @RequestParam int number, @RequestParam int size, @RequestParam String order, @RequestParam boolean reverse) throws ControllerException {
        try {
            long total = filmService.getTotalWithFilter(titre,director,size); 
            List<FilmDTO> films = filmService.findWithFilter(titre,director,number,size,order,reverse);
            PageDTO<FilmDTO> page = new PageDTO<FilmDTO>(number,size,total,films);
            return ResponseEntity.status(HttpStatus.OK).body(page);
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage());
        }
    }

    @GetMapping("count")
    @ApiOperation(value = "Renvoie le lastNamebre de films dans la BDD")
    public ResponseEntity<Long> getFilmsCount() throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(filmService.countFilms());
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage());
        }   
    }

    @PostMapping()
    @ApiOperation(value = "Add a film to database")
    public ResponseEntity<FilmDTO> createFilm(@RequestBody FilmDTO film) throws ControllerException {
        try {
            filmService.create(film);
            return ResponseEntity.status(HttpStatus.OK).body(film);
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage());
        }   
    }

    @DeleteMapping()
    @ApiOperation(value = "Delete film from data base")
    public ResponseEntity<String> deleteFilm(@RequestParam String id) throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(filmService.delete(id));
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage());
        }   
    }

    @PutMapping()
    @ApiOperation(value = "Update film in data base")
    public ResponseEntity<Integer> updateFilm(@RequestBody FilmDTO film) throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(filmService.update(film));
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage());
        }   
    }

}