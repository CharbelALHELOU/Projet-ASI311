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
    /**
     * Crée un FilmDTO à partir du parametre film
     * @param film
     * @return le FilmDTO crée
     */
    public static FilmDTO filmToFilmDTO(Film film, DirectorDTO director){
        return new FilmDTO(film.getId(),film.getTitre(), film.getDuration(),film.getDirectorID(),director.getFirstName()+" "+director.getLastName());
    }

     /**
     * Crée un Film à partir du parametre filmDTO
     * @param film
     * @return le Film crée
     */
    public static Film filmDTOToFilm(FilmDTO film){
        return new Film(film.getId(),film.getTitre(), film.getDuration(),film.getDirectorID());
    }

    /**
     * Crée une liste de FilmDTO à partir d'une liste de films donnée en paramètre
     * @param listFilm
     * @return la liste de FilmDTO crées
     */
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

    /**
     * Crée un Film a partir du FilmPojo en paramètre
     * @param film
     * @return le Film crée
     */
    public static Film filmPojoToFilm(FilmPojo film){
        return new Film(film.getId(),film.getTitre(), film.getDuration(),film.getDirectorID());
    }

    /**
     * Crée un FilmPojo a partir du Film en paramètre
     * @param film
     * @return le Film crée
     */
    public static FilmPojo filmToFilmPojo(Film film){
        return new FilmPojo(film.getId(),film.getTitre(), film.getDuration(),film.getDirectorID());
    }



    /**
     * Crée une liste de Film à partir d'une List<FilmPojo> en paramètre
     * @param listFilm
     * @return la List<Film> crée
     */
    public static List<Film> listFilmPojoToListFilm(List<FilmPojo> listFilm){
        List<Film> listFilmDTO = new ArrayList<>();
        for (FilmPojo film : listFilm ){
            listFilmDTO.add(filmPojoToFilm(film));
        }
        return listFilmDTO;
    }

}