/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author hoang
 */
public class JoinedGroup implements Serializable{
    public static final int ADMIN_LEAVE_GROUP = -2;
    public static final int DELETE_MEMBER = -1;
    public static final int IS_REQUEST = 0;
    public static final int IS_MEMBER = 1;
    public static final int IS_ADMIN = 2;
    public static final int UPDATE_MEMBER = 3;
    public static final int ADD_MEMBER = 4;
    private User user;
    private int position;

    public JoinedGroup() {
        super();
    }

    public JoinedGroup(User user, int position) {
        this.user = user;
        this.position = position;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}
