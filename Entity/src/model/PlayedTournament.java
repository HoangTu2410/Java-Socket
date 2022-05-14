package model;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayedTournament implements Serializable{
    private int id;
    private int gainedPoint;
    private User user;
    private ArrayList<PlayedGame> listPlayedGame;

    public PlayedTournament() {
        super();
    }

    public PlayedTournament(int gainedPoint, User user, ArrayList<PlayedGame> listPlayedGame) {
        super();
        this.gainedPoint = gainedPoint;
        this.user = user;
        this.listPlayedGame = listPlayedGame;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<PlayedGame> getListPlayedGame() {
        return listPlayedGame;
    }

    public void setListPlayedGame(ArrayList<PlayedGame> listPlayedGame) {
        this.listPlayedGame = listPlayedGame;
    }
    
}
