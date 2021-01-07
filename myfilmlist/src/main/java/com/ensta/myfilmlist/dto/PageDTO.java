package com.ensta.myfilmlist.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.*;

public class PageDTO<T> {
    private int number;
    private int size;
    private long total;
    private List<T> data;

    public PageDTO() {
    }

    public PageDTO(int number, int size, long total, List<T> data) {
        this.number = number;
        this.size = size;
        this.total = total;
        this.data = data;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getData() {
        return this.data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }


    @Override
    public int hashCode() {
        return Objects.hash(number, size, total, data);
    }


  
}
    
