package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Group implements Serializable{
    private int id;
    private String name;
    private String description;
    private ArrayList<JoinedGroup> listJoined;
     
    public Group() {
        super();
    }

    public Group(String name, String description, ArrayList<JoinedGroup> listJoined) {
        this.name = name;
        this.description = description;
        this.listJoined = listJoined;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<JoinedGroup> getListJoined() {
        return listJoined;
    }

    public void setListJoined(ArrayList<JoinedGroup> listJoined) {
        this.listJoined = listJoined;
    }

}
