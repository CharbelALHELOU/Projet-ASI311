package com.ensta.myfilmlist.controller;

import java.util.List;

import com.ensta.myfilmlist.dto.DirectorDTO;
import com.ensta.myfilmlist.exception.ControllerException;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.service.DirectorService;

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


@RestController("DirectorWS")
@RequestMapping(value = "/director")
@CrossOrigin(origins = "http://localhost:4200")
public class DirectorWS {

    private DirectorService directorService;

    public DirectorWS(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping("count")
    @ApiOperation(value = "Return nbre of directors")
    public ResponseEntity<Long> getDirectorCount() throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(directorService.countDirector());
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage());
        }   
    }

    @GetMapping
    @ApiOperation(value = "Get list of directors")
    public ResponseEntity<List<DirectorDTO>> retrieveDirector() throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(directorService.findAll());
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage());
        }
    }

    @GetMapping("{id}")
    @ApiOperation(value = "Get a director")
    public ResponseEntity<DirectorDTO> retrieveDirectorById(@RequestParam String id) throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(directorService.findById(id));
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage());
        }
    }

    @PostMapping()
    @ApiOperation(value = "Add a director to data base")
    public ResponseEntity<DirectorDTO> createDirector(@RequestBody DirectorDTO director) throws ControllerException {
        try {
            directorService.create(director);
            return ResponseEntity.status(HttpStatus.OK).body(director);
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage());
        }   
    }


    @DeleteMapping()
    @ApiOperation(value = "Delete director from data base")
    public ResponseEntity<String> deleteDirector(@RequestParam String id) throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(directorService.delete(id));
            // VOIR POUR METTRE UN IF AVEC UN STATUT "NOT FOUND"
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage());
        }   
    }

    @PutMapping()
    @ApiOperation(value = "Update a director")
    public ResponseEntity<Integer> updateDirector(@RequestBody DirectorDTO director) throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(directorService.update(director));
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage());
        }   
    }

}