package com.example.guiex1.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Utilizator extends Entity<Long>{
    private String firstName;
    private String lastName;
    private String password;
    private String username;
    private List<Utilizator> friends;

    public Utilizator(){}
    public Utilizator(String firstName, String lastName, String password,String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password  =  password;
        this.username = username;

    }
    public String getUsername()
    {
        return username;
    }
    public String getPassword()
    {
        return  password;
    }
    public String getFirstName() {
        return firstName;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Utilizator> getFriends() {
        return friends;
    }

    @Override
    public String toString() {
        return "Utilizator{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", friends=" + friends +
                '}';
    }
    public void setFriends(List<Utilizator> friends) {
        this.friends = friends;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utilizator)) return false;
        Utilizator that = (Utilizator) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getPassword(),getFriends());
    }
}