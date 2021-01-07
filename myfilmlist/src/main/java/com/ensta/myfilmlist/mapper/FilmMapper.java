package com.ensta.myfilmlist.mapper;

import java.util.ArrayList;
import java.util.List;

import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.dto.DirectorDTO;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.Director;
import com.ensta.myfilmlist.pojo.FilmPojo;
import com.ensta.myfilmlist.service.DirectorService;

public class FilmMapper {

    public static FilmDTO filmToFilmDTO(Film film, DirectorDTO director){
        return new FilmDTO(film.getId(),film.getTitre(), film.getDuration(),film.getDirectorID(),director.getFirstName()+" "+director.getLastName());
    }


    public static Film filmDTOToFilm(FilmDTO film){
        return new Film(film.getId(),film.getTitre(), film.getDuration(),film.getDirectorID());
    }


    public static List<FilmDTO> listFilmToListFilmDTO(List<Film> listFilm, List<DirectorDTO> listDirectorDTO){
        List<FilmDTO> listFilmDTO = new ArrayList<>();
        for (Film film : listFilm ){
            int directorID = film.getDirectorID(); 
            for (int pos = 0; pos < listDirectorDTO.size(); pos++) {
                if (directorID == listDirectorDTO.get(pos).getId()) {
                    listFilmDTO.add(filmToFilmDTO(film,listDirectorDTO.get(pos)));
                } 
            }
        }
        return listFilmDTO;
    }

    public static Film filmPojoToFilm(FilmPojo film){
        return new Film(film.getId(),film.getTitre(), film.getDuration(),film.getDirectorID());
    }

    public static FilmPojo filmToFilmPojo(Film film){
        return new FilmPojo(film.getId(),film.getTitre(), film.getDuration(),film.getDirectorID());
    }

    public static List<Film> listFilmPojoToListFilm(List<FilmPojo> listFilm){
        List<Film> listFilmDTO = new ArrayList<>();
        for (FilmPojo film : listFilm ){
            listFilmDTO.add(filmPojoToFilm(film));
        }
        return listFilmDTO;
    }

}