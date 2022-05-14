package model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

public class Tournament implements Serializable{
    private int id;
    private String name;
    private Date startDate;
    private Date endDate;
    private float prize;
    private ArrayList<PlayedTournament> listPlayedTournament;

    public Tournament() {
        super();
    }

    public Tournament(String name, Date startDate, Date endDate, float prize, ArrayList<PlayedTournament> listPlayedTournament) {
        super();
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.prize = prize;
        this.listPlayedTournament = listPlayedTournament;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public float getPrize() {
        return prize;
    }

    public void setPrize(float prize) {
        this.prize = prize;
    }

    public ArrayList<PlayedTournament> getListPlayedTournament() {
        return listPlayedTournament;
    }

    public void setListPlayedTournament(ArrayList<PlayedTournament> listPlayedTournament) {
        this.listPlayedTournament = listPlayedTournament;
    }

    
}
    