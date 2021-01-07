package com.ensta.myfilmlist.mapper;

import java.util.ArrayList;
import java.util.List;

import com.ensta.myfilmlist.dto.DirectorDTO;
import com.ensta.myfilmlist.model.Director;
import com.ensta.myfilmlist.pojo.DirectorPojo;

public class DirectorMapper {


    public static DirectorDTO directorToDirectorDTO(Director director){
        return new DirectorDTO(director.getId(),director.getLastName(), director.getFirstName(),director.getDate());
    }


    public static Director directorDTOToDirector(DirectorDTO director){
        return new Director(director.getId(),director.getLastName(), director.getFirstName(),director.getDate());
    }


    public static List<DirectorDTO> listDirectorToListDirectorDTO(List<Director> listDirector){
        List<DirectorDTO> listDirectorDTO = new ArrayList<>();
        for (Director director : listDirector ){
            listDirectorDTO.add(directorToDirectorDTO(director));
        }
        return listDirectorDTO;
    }

    public static Director directorPojoToDirector(DirectorPojo director){
        return new Director(director.getId(),director.getLastName(), director.getFirstName(),director.getDate());
    }

    public static DirectorPojo directorToDirectorPojo(Director director){
        return new DirectorPojo(director.getId(),director.getLastName(), director.getFirstName(),director.getDate());
    }



    public static List<Director> listDirectorPojoToListDirector(List<DirectorPojo> listDirector){
        List<Director> listDirectorDTO = new ArrayList<>();
        for (DirectorPojo director : listDirector ){
            listDirectorDTO.add(directorPojoToDirector(director));
        }
        return listDirectorDTO;
    }

}