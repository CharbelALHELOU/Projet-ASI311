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

    public List<DirectorDTO> findAll() throws ServiceException {
        List<Director> listDirectorModel;
        try {
            listDirectorModel = DirectorMapper.listDirectorPojoToListDirector(directorDAO.findAll());
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return DirectorMapper.listDirectorToListDirectorDTO(listDirectorModel);
    }

    public long countDirector() throws ServiceException {
        long total; 
        try {
            total =  directorDAO.countDirector();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return total;
    }

    public DirectorDTO findById(String id) throws ServiceException {
        Director directorModel;
        try {
            directorModel = DirectorMapper.directorPojoToDirector(directorDAO.findById(id));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return DirectorMapper.directorToDirectorDTO(directorModel);
    }

    public void create(DirectorDTO director) throws ServiceException {
        try {
           directorDAO.create(DirectorMapper.directorToDirectorPojo(DirectorMapper.directorDTOToDirector(director)));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public String delete(String id) throws ServiceException {
        try {
           String returnId = directorDAO.delete(id);
           return returnId;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public Integer update(DirectorDTO director) throws ServiceException {
        try {
           return directorDAO.update(DirectorMapper.directorToDirectorPojo(DirectorMapper.directorDTOToDirector(director)));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}