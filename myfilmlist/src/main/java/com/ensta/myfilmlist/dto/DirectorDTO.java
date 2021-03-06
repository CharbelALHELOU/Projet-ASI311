package com.ensta.myfilmlist.dto;

import java.sql.Date;

import com.ensta.myfilmlist.model.Director;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class DirectorDTO {
    private long id;
    private String lastName;
    private String firstName;
    private Date date; 

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
  
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    } 

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    } 

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    } 

    public DirectorDTO() {};
    
    public DirectorDTO(String lastName, String firstName, Date date) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.date = date;
    }

    public DirectorDTO(long id,String lastName, String firstName, Date date) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.date = date;
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