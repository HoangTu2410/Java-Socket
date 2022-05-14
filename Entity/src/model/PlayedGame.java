package model;

import java.io.Serializable;

public class PlayedGame implements Serializable{
    private int id;
    private int gainedPoint;
    private Boolean result;
    private Character character;
    private User user;

    public PlayedGame() {
        super();
    }

    public PlayedGame(int gainedPoint, Boolean result, Character character, User user) {
        super();
        this.gainedPoint = gainedPoint;
        this.result = result;
        this.character = character;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGainedPoint() {
        return gainedPoint;
    }

    public void setGainedPoint(int gainedPoint) {
        this.gainedPoint = gainedPoint;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
