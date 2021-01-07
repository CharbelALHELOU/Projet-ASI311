package com.ensta.myfilmlist.dto;

import com.ensta.myfilmlist.model.Film;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class FilmDTO {
    private long id;
    private String titre;
    private int duration;
    private int directorID;
    private String directorName;

    public String getTitre() {
        return titre;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public int getDirectorID() {
        return directorID;
    }

    public void setDirectorID(int directorID) {
        this.directorID = directorID;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public FilmDTO() {};
    
    public FilmDTO(String titre, int duration, int directorID, String directorName) {
        this.titre = titre;
        this.duration = duration;
        this.directorID = directorID;
        this.directorName = directorName;
    }

    public FilmDTO(long id, String titre, int duration, int directorID, String directorName) {
        this.id = id;
        this.titre = titre;
        this.duration = duration;
        this.directorID = directorID;
        this.directorName = directorName;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

}