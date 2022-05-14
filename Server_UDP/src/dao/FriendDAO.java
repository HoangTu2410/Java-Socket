/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import model.Friend;
import model.User;
/**
 *
 * @author hoang
 */
public class FriendDAO extends DAO{
    public FriendDAO(){
        super();
    }
    
    public ArrayList<User> searchFriends(User user){
        ArrayList<User> result = new ArrayList<User>();
        String sql = "select * from gamemario.tbluser where tbluser.id in"
                + "(select friendid from gamemario.tblfriends where tblfriends.userid = ? and status = ?) "
                + "or tbluser.id in(select userid from gamemario.tblfriends where tblfriends.friendid = ? and status = ?);";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,user.getId());
            ps.setInt(2,1);
            ps.setInt(3,user.getId());
            ps.setInt(4,1);
            ResultSet rs = ps.executeQuery();
 
            while(rs.next()){
                User us = new User();
                us.setId(rs.getInt("id"));
                us.setEmail(rs.getString("email"));
                us.setPassword(rs.getString("password"));
                us.setName(rs.getString("name"));
                us.setGender(rs.getInt("gender")==0);
                us.setDateOfBirth(rs.getDate("dateofbirth"));
                us.setDescription(rs.getString("description"));
                result.add(us);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    
    public boolean requestFriend(Friend friends){
        String sql = "INSERT INTO gamemario.tblfriends(userid, friendid, status) VALUES(?,?,?)";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, friends.getUser1().getId());
            ps.setInt(2, friends.getUser2().getId());
            ps.setInt(3, friends.getStatus());
 
            ps.executeUpdate();
             
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean addFriend(Friend friends){
        String sql = "UPDATE gamemario.tblfriends SET status = ? "
                + "WHERE (userid = ? AND friendid = ?) OR (userid = ? AND friendid = ?)";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, friends.getStatus());
            ps.setInt(2, friends.getUser1().getId());
            ps.setInt(3, friends.getUser2().getId());
            ps.setInt(4, friends.getUser2().getId());
            ps.setInt(5, friends.getUser1().getId());
 
            ps.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean deleteFriend(Friend friends){
        String sql = "DELETE FROM gamemario.tblfriends WHERE (userid = ? AND friendid = ?) OR (userid = ? AND friendid = ?)";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, friends.getUser1().getId());
            ps.setInt(2, friends.getUser2().getId());
            ps.setInt(3, friends.getUser2().getId());
            ps.setInt(4, friends.getUser1().getId());
 
            ps.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public ArrayList<User> searchRequestFriend(User user){
        ArrayList<User> result = new ArrayList<User>();
        String sql = "select * from gamemario.tbluser where tbluser.id in"
                + "(select userid from gamemario.tblfriends where tblfriends.friendid = ? and status = ?);";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,user.getId());
            ps.setInt(2,Friend.IS_REQUEST);
            ResultSet rs = ps.executeQuery();
 
            while(rs.next()){
                User us = new User();
                us.setId(rs.getInt("id"));
                us.setEmail(rs.getString("email"));
                us.setPassword(rs.getString("password"));
                us.setName(rs.getString("name"));
                us.setGender(rs.getInt("gender")==0);
                us.setDateOfBirth(rs.getDate("dateofbirth"));
                us.setDescription(rs.getString("description"));
                result.add(us);
            }
        }catch(Exception e){
            e.printStackTrace();
        }   
        return result;
    }
    public boolean checkFriend(Friend friends){
        boolean result = false;
        int id = friends.getUser2().getId();
        String sql = "select userid, friendid from gamemario.tblfriends where "
                + "tblfriends.friendid = ? or tblfriends.userid = ?";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, friends.getUser1().getId());
            ps.setInt(2, friends.getUser1().getId());
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                int id1 = rs.getInt("userid");
                int id2 = rs.getInt("friendid");
                if(id1==id || id2 ==id) result = true;
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
