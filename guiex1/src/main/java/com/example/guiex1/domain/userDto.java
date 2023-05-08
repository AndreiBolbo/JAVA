package com.example.guiex1.domain;

import java.time.LocalDateTime;
import java.util.Date;

public class userDto {
    private String username;
    private Date date;
    private String status;
    public userDto(String username, Date date,String status) {
        this.username = username;
        this.date = date;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;

    }

}
