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


    /**
     * Count directors
     * @return le statut de la requête
     * @throws ControllerException problème lors de l'appel au service
     */
    @GetMapping("count")
    @ApiOperation(value = "Return nbre of directors")
    public ResponseEntity<Long> getRealsCount() throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(directorService.countReals());
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage());
        }   
    }
    
     /**
     * Get list of directors from the service
     * @return status
     * @throws ControllerException problem calling the service
     */
    @GetMapping
    @ApiOperation(value = "Get list of directors")
    public ResponseEntity<List<DirectorDTO>> retrieveDirector() throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(directorService.findAll());
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage());
        }
    }

    /**
     * Get a director from an id
     * @return status
     * @throws ControllerException problem calling the service
     */
    @GetMapping("{id}")
    @ApiOperation(value = "Get a director")
    public ResponseEntity<DirectorDTO> retrieveDirectorById(@RequestParam String id) throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(directorService.findById(id));
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage());
        }
    }

    /**
     * Crée un director dans la BDD
     * @return le statut de la requête
     * @throws ControllerException problème lors de l'appel au service
     */
    @PostMapping()
    @ApiOperation(value = "Créer un director dans la BDD")
    public ResponseEntity<DirectorDTO> createDirector(@RequestBody DirectorDTO director) throws ControllerException {
        try {
            directorService.create(director);
            return ResponseEntity.status(HttpStatus.OK).body(director);
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage());
        }   
    }

    /**
     * Supprime un director de la BDD
     * @return le statut de la requête
     * @throws ControllerException problème lors de l'appel au service
     */
    @DeleteMapping()
    @ApiOperation(value = "Supprime un director de la BDD")
    public ResponseEntity<String> deleteDirector(@RequestParam String id) throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(directorService.delete(id));
            // VOIR POUR METTRE UN IF AVEC UN STATUT "NOT FOUND"
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage());
        }   
    }

    /**
     * Update les champs d'un director avec ceux du directorl donné en paramètre
     * @return le statut de la requete
     * @throws ControllerException problème lors de l'appel au service 
     */
    @PutMapping()
    @ApiOperation(value = "Update un director de la BDD")
    public ResponseEntity<Integer> updateDirector(@RequestBody DirectorDTO director) throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(directorService.update(director));
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage());
        }   
    }

}