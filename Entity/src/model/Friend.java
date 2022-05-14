/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author hoang
 */
public class Friend implements Serializable{
    public static final int IS_REQUEST = 0;
    public static final int IS_FRIEND = 1;
    private User user1;
    private User user2;
    private int status;

    public Friend() {
        super();
    }

    public Friend(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

    public Friend(User user1, User user2, int status) {
        super();
        this.user1 = user1;
        this.user2 = user2;
        this.status = status;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
}
