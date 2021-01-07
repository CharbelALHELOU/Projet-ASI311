package com.ensta.myfilmlist.service;

import java.util.List;

import com.ensta.myfilmlist.dao.FilmDAO;
import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.dto.DirectorDTO;
import com.ensta.myfilmlist.exception.DaoException;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.mapper.FilmMapper;
import com.ensta.myfilmlist.mapper.DirectorMapper;
import com.ensta.myfilmlist.model.Film;

import org.springframework.stereotype.Service;

import jdk.nashorn.internal.ir.ReturnNode;

@Service
public class FilmService {

    private FilmDAO filmDAO;

    private DirectorService directorService;

    public FilmService(FilmDAO filmDAO, DirectorService directorService) {
        this.filmDAO = filmDAO;
        this.directorService = directorService;
    }

    public List<FilmDTO> findAll(int number,int size, String order, boolean sens) throws ServiceException {
        List<Film> listFilmModel;
        List<DirectorDTO> listDirectorDTO;
        try {
            listFilmModel = FilmMapper.listFilmPojoToListFilm(filmDAO.findAll(number,size, order, sens));
            listDirectorDTO = directorService.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return FilmMapper.listFilmToListFilmDTO(listFilmModel,listDirectorDTO);
    }

    public long getTotal(int size) throws ServiceException {
        long total; 
        try {
            total =  filmDAO.countFilms()/size + 1;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return total;
    }

    public long countFilms() throws ServiceException {
        long total; 
        try {
            total =  filmDAO.countFilms();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return total;
    }

    public long getTotalWithFilter(String titre, String director, int size) throws ServiceException {
        long total; 
        try {
            total = filmDAO.countFilmsFiltered(titre,director)/size + 1 ;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return total;
    }

    public FilmDTO findById(String id) throws ServiceException {
        Film filmModel;
        DirectorDTO directorDTO;
        try {
            filmModel = FilmMapper.filmPojoToFilm(filmDAO.findById(id));
            directorDTO = directorService.findById(Integer.toString(filmModel.getDirectorID()));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return FilmMapper.filmToFilmDTO(filmModel, directorDTO);
    }

    public List<FilmDTO> findWithFilter(String titre, String director, int number, int size, String order, boolean reverse) throws ServiceException {
        List<Film> listFilmModel;
        List<DirectorDTO> listDirectorDTO;
        try {
            listFilmModel = FilmMapper.listFilmPojoToListFilm(filmDAO.findWithFilter(titre, director, number, size, order, reverse));
            listDirectorDTO = directorService.findAll(); 
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return FilmMapper.listFilmToListFilmDTO(listFilmModel,listDirectorDTO);
    }

    public void create(FilmDTO film) throws ServiceException {
        try {
           filmDAO.create(FilmMapper.filmToFilmPojo(FilmMapper.filmDTOToFilm(film)));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public String delete(String id) throws ServiceException {
        try {
           String returnId = filmDAO.delete(id);
           return returnId;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public Integer update(FilmDTO film) throws ServiceException {
        try {
           return filmDAO.update(FilmMapper.filmToFilmPojo(FilmMapper.filmDTOToFilm(film)));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}