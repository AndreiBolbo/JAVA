package com.example.guiex1.domain;

import java.time.LocalDateTime;
import java.util.Date;


public class Prietenie  {
    private Long id1;
   private Long id2;
   private Date date;
    private String status;

    public Prietenie(Long id1,Long id2, Date date,String status) {
        this.id1 = id1;
        this.id2= id2;
        this.date = date;
        this.status = status;
    }

    public Long getId1() {
        return id1;
    }

    public Long getId2() {
        return id2;
    }

    public String getStatus() {
        return status;
    }

    public void setId1(Long id1) {
        this.id1 = id1;
    }

    public void setId2(Long id2) {
        this.id2 = id2;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return the date when the friendship was created
     */
    public Date getDate() {
        return date;
    }
    public String toString() {
        return "Prietenie{" +
                "id1='" + id1 + '\'' +
                ",id2='" + id2 + '\'' +
                ", status=" + status +
                ",data="+ date +
                '}';
    }

}
