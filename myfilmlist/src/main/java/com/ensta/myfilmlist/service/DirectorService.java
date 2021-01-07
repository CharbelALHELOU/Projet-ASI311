package com.ensta.myfilmlist.service;

import java.util.List;

import com.ensta.myfilmlist.dao.DirectorDAO;
import com.ensta.myfilmlist.dto.DirectorDTO;
import com.ensta.myfilmlist.exception.DaoException;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.mapper.DirectorMapper;
import com.ensta.myfilmlist.model.Director;

import org.springframework.stereotype.Service;

import jdk.nashorn.internal.ir.ReturnNode;

@Service
public class DirectorService {

    private DirectorDAO directorDAO;

    public DirectorService(DirectorDAO directorDAO) {
        this.directorDAO = directorDAO;
    }

    /**
     * Récupère la liste des directors en List<DirectorPojo>, l'envoie au mapper pour récupérer la liste en modèle, puis renvoie ce modèle au mapper pour recevoir la liste en List<DirectorDTO> qui est finalement retournée
     * @return la liste de tous les directors au format List<DirectorDTO>
     * @throws ServiceException problème lors de l'accès au DAO
     */
    public List<DirectorDTO> findAll() throws ServiceException {
        List<Director> listDirectorModel;
        try {
            listDirectorModel = DirectorMapper.listDirectorPojoToListDirector(directorDAO.findAll());
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return DirectorMapper.listDirectorToListDirectorDTO(listDirectorModel);
    }

    /**
     * renvoie le lastNamebre total de films
     * @return le lastNamebre total de films
     * @throws ServiceExceptionproblème lors de l'accès au DAO
     */
    public long countReals() throws ServiceException {
        long total; 
        try {
            total =  directorDAO.countReals();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return total;
    }

    /**
     * Récupère un director a partir de son ID
     * @return le director cherché
     * @throws ServiceException problème lors de l'accès au DAO
     */
    public DirectorDTO findById(String id) throws ServiceException {
        Director directorModel;
        try {
            directorModel = DirectorMapper.directorPojoToDirector(directorDAO.findById(id));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return DirectorMapper.directorToDirectorDTO(directorModel);
    }

    /**
     * Récupère la liste filtrée des directors en List<DirectorPojo>, l'envoie au mapper pour récupérer la liste en modèle, puis renvoie ce modèle au mapper pour recevoir la liste en List<DirectorDTO> qui est finalement retournée
     * @return la liste filtrée des directors au format List<DirectorDTO>
     * @throws ServiceException problème lors de l'accès au DAO
     */
    public List<DirectorDTO> findWithFilter(String real) throws ServiceException {
        List<Director> listDirectorModel;
        try {
            listDirectorModel = DirectorMapper.listDirectorPojoToListDirector(directorDAO.findWithFilter(real));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return DirectorMapper.listDirectorToListDirectorDTO(listDirectorModel);
    }

    /**
     * Créer un director dans la BDD
     * @return le director crée
     * @throws ServiceException problème lors de l'accès au DAO
     */
    public void create(DirectorDTO director) throws ServiceException {
        try {
           directorDAO.create(DirectorMapper.directorToDirectorPojo(DirectorMapper.directorDTOToDirector(director)));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Supprime un director de la BDD
     * @return le director supprimé
     * @throws ServiceException problème lors de l'accès au DAO
     */
    public String delete(String id) throws ServiceException {
        try {
           String returnId = directorDAO.delete(id);
           return returnId;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    
    /**
     * Update un director dans la BDD
     * @return le director mis à jour
     * @throws ServiceException problème lors de l'accès au DAO
     */
    public Integer update(DirectorDTO director) throws ServiceException {
        try {
           return directorDAO.update(DirectorMapper.directorToDirectorPojo(DirectorMapper.directorDTOToDirector(director)));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}