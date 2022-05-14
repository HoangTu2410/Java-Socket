package model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

public class User implements Serializable{
    private int id;
    private String email;
    private String password;
    private String name;
    private Boolean gender;
    private Date dateOfBirth;
    private String description;

    public User() {
        super();
    }

    public User(String email, String password, String name, Boolean gender, Date dateOfBirth, String description) {
        super();
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
