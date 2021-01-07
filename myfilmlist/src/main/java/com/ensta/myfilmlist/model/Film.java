package com.ensta.myfilmlist.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Film {
    private long id;
    private String titre;
    private int duration;
    private int directorID;

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

    public Film(String titre, int duration, int directorID) {
        this.titre = titre;
        this.duration = duration;
        this.directorID = directorID;

    }

    public Film(long id, String titre, int duration, int directorID) {
        this.id = id;
        this.titre = titre;
        this.duration = duration;
        this.directorID = directorID;

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