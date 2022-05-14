/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author hoang
 */
import java.io.Serializable;
 
public class ObjectWrapper  implements Serializable{
    public static final int LOGIN_USER = 1;
    public static final int REPLY_LOGIN_USER = 2;
    public static final int ADD_USER = 3;
    public static final int REPLY_ADD_USER = 4;
    public static final int EDIT_USER = 5;
    public static final int REPLY_EDIT_USER = 6;
    public static final int SEARCH_USER = 7;
    public static final int REPLY_SEARCH_USER = 8;
    public static final int FRIENDS = 9;
    public static final int REPLY_FRIENDS = 10;
    public static final int SEARCH_REQUEST_FRIEND = 11;
    public static final int REPLY_SEARCH_REQUEST_FRIEND = 12;
    public static final int ADD_FRIEND = 13;
    public static final int REPLY_ADD_FRIEND = 14;
    public static final int REQUEST_FRIEND = 15;
    public static final int REPLY_REQUEST_FRIEND = 16;
    public static final int DELETE_FRIEND = 17;
    public static final int REPLY_DELETE_FRIEND = 18;
    public static final int RANKING = 19;
    public static final int REPLY_RANKING = 20;
    public static final int CHECK_FRIEND = 21;
    public static final int REPLY_CHECK_FRIEND = 22;
    public static final int SERVER_CLIENT_ONLINE = 23;
    public static final int GROUP = 24;
    public static final int REPLY_GROUP = 25;
    public static final int SEARCH_GROUP = 26;
    public static final int REPLY_SEARCH_GROUP = 27;
    public static final int CREATE_GROUP = 28;
    public static final int REPLY_CREATE_GROUP = 29;
    public static final int DELETE_MEMBER = 30;
    public static final int REPLY_DELETE_MEMBER = 31;
    public static final int REQUEST_GROUP = 32;
    public static final int REPLY_REQUEST_GROUP = 33;
    public static final int ADD_MEMBER = 34;
    public static final int REPLY_ADD_MEMBER = 35;
    public static final int EDIT_GROUP = 36;
    public static final int REPLY_EDIT_GROUP = 37;
    public static final int CHALLENGE = 38;
    public static final int REPLY_CHALLENGE = 39;
    public static final int SERVER_SEND_CLIENT_CHALLENGE = 40;
    public static final int CLIENT_SEND_SERVER_REQUEST_CHALLENGE = 41;
    public static final int REPLY_CLIENT_SEND_SERVER_REQUEST_CHALLENGE = 42;
    public static final int SERVER_SEND_CLIENT_REQUEST_CHALLENGE = 43;
    public static final int SAVE_GAME = 44;
    public static final int REPLY_SAVE_GAME = 45;
    public static final int SEND_CHARACTER = 46;
    public static final int REPLY_SEND_CHARACTER = 47;
    public static final int SERVER_SEND_CLIENT_JOIN_GAME = 48;
    public static final int SAVE_CHARACTER = 49;
    public static final int REPLY_SAVE_CHARACTER = 50;
     
    private int performative;
    private Object data;
    public ObjectWrapper() {
        super();
    }
    public ObjectWrapper(int performative, Object data) {
        super();
        this.performative = performative;
        this.data = data;
    }
    public int getPerformative() {
        return performative;
    }
    public void setPerformative(int performative) {
        this.performative = performative;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }   
}