package com.ensta.myfilmlist.mapper;

import java.util.ArrayList;
import java.util.List;

import com.ensta.myfilmlist.dto.DirectorDTO;
import com.ensta.myfilmlist.model.Director;
import com.ensta.myfilmlist.pojo.DirectorPojo;

public class DirectorMapper {

    /**
     * Crée un DirectorDTO à partir du parametre director
     * @param director
     * @return le DirectorDTO crée
     */
    public static DirectorDTO directorToDirectorDTO(Director director){
        return new DirectorDTO(director.getId(),director.getLastName(), director.getFirstName(),director.getDate());
    }

     /**
     * Crée un Director à partir du parametre directorDTO
     * @param director
     * @return le Director crée
     */
    public static Director directorDTOToDirector(DirectorDTO director){
        return new Director(director.getId(),director.getLastName(), director.getFirstName(),director.getDate());
    }

    /**
     * Crée une liste de DirectorDTO à partir d'une liste de directors donnée en paramètre
     * @param listDirector
     * @return la liste de DirectorDTO crées
     */
    public static List<DirectorDTO> listDirectorToListDirectorDTO(List<Director> listDirector){
        List<DirectorDTO> listDirectorDTO = new ArrayList<>();
        for (Director director : listDirector ){
            listDirectorDTO.add(directorToDirectorDTO(director));
        }
        return listDirectorDTO;
    }

    /**
     * Crée un Director a partir du DirectorPojo en paramètre
     * @param director
     * @return le Director crée
     */
    public static Director directorPojoToDirector(DirectorPojo director){
        return new Director(director.getId(),director.getLastName(), director.getFirstName(),director.getDate());
    }

    /**
     * Crée un Director a partir du DirectorPojo en paramètre
     * @param director
     * @return le Director crée
     */
    public static DirectorPojo directorToDirectorPojo(Director director){
        return new DirectorPojo(director.getId(),director.getLastName(), director.getFirstName(),director.getDate());
    }



    /**
     * Crée une liste de Director à partir d'une List<DirectorPojo> en paramètre
     * @param listDirector
     * @return la List<Director> crée
     */
    public static List<Director> listDirectorPojoToListDirector(List<DirectorPojo> listDirector){
        List<Director> listDirectorDTO = new ArrayList<>();
        for (DirectorPojo director : listDirector ){
            listDirectorDTO.add(directorPojoToDirector(director));
        }
        return listDirectorDTO;
    }

}