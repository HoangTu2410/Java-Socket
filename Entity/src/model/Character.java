package model;

import java.io.Serializable;
 
public class Character implements Serializable{
    private int id;
    private String name;
    private String image;
     
    public Character() {
        super();
    }

    public Character(String name, String image) {
        super();
        this.name = name;
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
