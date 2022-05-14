package model;

import java.io.Serializable;
 
public class Game implements Serializable{
    private int id;
    private PlayedGame player1;
    private PlayedGame player2;
    private int status;
    public static final int REQUEST_CHALLENGE = 0;
    public static final int PLAYING = 1;
    public static final int FINISHED = 2;
    public static final int REFUSE_CHALLENGE = 3;
    public static final int ACCEPT_CHALLENGE = 4;
    
    public Game() {
        super();
    }

    public Game(PlayedGame player1, PlayedGame player2, int status) {
        this.player1 = player1;
        this.player2 = player2;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PlayedGame getPlayer1() {
        return player1;
    }

    public void setPlayer1(PlayedGame player1) {
        this.player1 = player1;
    }

    public PlayedGame getPlayer2() {
        return player2;
    }

    public void setPlayer2(PlayedGame player2) {
        this.player2 = player2;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}
