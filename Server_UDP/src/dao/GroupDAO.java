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
import java.sql.Date;
import model.Group;
import model.JoinedGroup;
import model.User;
/**
 *
 * @author hoang
 */
public class GroupDAO extends DAO{

    public GroupDAO() {
        super();
    }
    
    public ArrayList<Group> searchGroup(User user){
        ArrayList<Group> result = new ArrayList<Group>();
        String sql1 = "select id, name, description from gamemario.tblgroup where id in"
                + " (select groupid from gamemario.tbljoinedgroup where userid = ?)";
        try{
            PreparedStatement ps1 = con.prepareStatement(sql1);
            ps1.setInt(1, user.getId());
            ResultSet rs1 = ps1.executeQuery();
 
            while(rs1.next()){
                Group group = new Group();
                group.setId(rs1.getInt("id"));
                group.setName(rs1.getString("name"));
                group.setDescription(rs1.getString("description"));
                
                ArrayList<JoinedGroup> joined = new ArrayList<JoinedGroup>();
                String sql2 = "select tbluser.*, tbljoinedgroup.position from gamemario.tbluser, gamemario.tbljoinedgroup"
                        + " where tbluser.id = tbljoinedgroup.userid and tbljoinedgroup.groupid = ?;";
                try{
                    PreparedStatement ps2 = con.prepareStatement(sql2);
                    ps2.setInt(1, group.getId());
                    ResultSet rs2 = ps2.executeQuery();
                    while(rs2.next()){
                        User us = new User();
                        us.setId(rs2.getInt("id"));
                        us.setEmail(rs2.getString("email"));
                        us.setPassword(rs2.getString("password"));
                        us.setName(rs2.getString("name"));
                        us.setGender(rs2.getInt("gender")==0?true:false);
                        us.setDateOfBirth(rs2.getDate("dateofbirth"));
                        us.setDescription(rs2.getString("description"));
                        int position = rs2.getInt("position");
                        JoinedGroup jg = new JoinedGroup(us, position);
                        joined.add(jg);
                    }
                }catch(Exception ex){
                    ex.printStackTrace();
                }
                group.setListJoined(joined);
                
                result.add(group);
            }
        }catch(Exception e){
            e.printStackTrace();
        }   
        return result;
    }
    
    public ArrayList<Group> searchGroup(String key){
        ArrayList<Group> result = new ArrayList<Group>();
        String sql = "SELECT * FROM gamemario.tblgroup WHERE name LIKE ?";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + key + "%");
            ResultSet rs = ps.executeQuery();
 
            while(rs.next()){
                Group group = new Group();
                group.setId(rs.getInt("id"));
                group.setName(rs.getString("name"));
                group.setDescription(rs.getString("description"));
                
                ArrayList<JoinedGroup> joined = new ArrayList<JoinedGroup>();
                String sql2 = "select tbluser.*, tbljoinedgroup.position from gamemario.tbluser, gamemario.tbljoinedgroup"
                        + " where tbluser.id = tbljoinedgroup.userid and tbljoinedgroup.groupid = ?;";
                try{
                    PreparedStatement ps2 = con.prepareStatement(sql2);
                    ps2.setInt(1, group.getId());
                    ResultSet rs2 = ps2.executeQuery();
                    while(rs2.next()){
                        User us = new User();
                        us.setId(rs2.getInt("id"));
                        us.setEmail(rs2.getString("email"));
                        us.setPassword(rs2.getString("password"));
                        us.setName(rs2.getString("name"));
                        us.setGender(rs2.getInt("gender")==0?true:false);
                        us.setDateOfBirth(rs2.getDate("dateofbirth"));
                        us.setDescription(rs2.getString("description"));
                        int position = rs2.getInt("position");
                        JoinedGroup jg = new JoinedGroup(us, position);
                        joined.add(jg);
                    }
                }catch(Exception ex){
                    ex.printStackTrace();
                }
                group.setListJoined(joined);
                
                result.add(group);
            }
        }catch(Exception e){
            e.printStackTrace();
        }   
        return result;
    }
    
    public boolean createGroup(Group group){
        String sql1 = "INSERT INTO gamemario.tblgroup(name, description) VALUES(?,?)";
        String sql2 = "INSERT INTO gamemario.tbljoinedgroup(userid, groupid, position) VALUES(?,?,?)";
        try{
            PreparedStatement ps1 = con.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
            ps1.setString(1, group.getName());
            ps1.setString(2, group.getDescription());
            ps1.executeUpdate();
            
            ResultSet generatedKeys = ps1.getGeneratedKeys();
            if (generatedKeys.next()) {
                group.setId(generatedKeys.getInt(1));
            }
            
            PreparedStatement ps2 = con.prepareStatement(sql2);
            ps2.setInt(1, group.getListJoined().get(0).getUser().getId());
            ps2.setInt(2, group.getId());
            ps2.setInt(3, group.getListJoined().get(0).getPosition());
            ps2.executeUpdate();
             
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean requestGroup(Group group){
        String sql = "INSERT INTO gamemario.tbljoinedgroup(userid, groupid, position) VALUES(?,?,?)";
        try{
            int k = group.getListJoined().size();
            PreparedStatement ps2 = con.prepareStatement(sql);
            ps2.setInt(1, group.getListJoined().get(k-1).getUser().getId());
            ps2.setInt(2, group.getId());
            ps2.setInt(3, group.getListJoined().get(k-1).getPosition());
            ps2.executeUpdate();
             
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean deleteMember(Group group){
        String sql1 = "UPDATE gamemario.tbljoinedgroup SET position = ? WHERE (userid = ? AND groupid = ?)";
        String sql2 = "DELETE FROM gamemario.tbljoinedgroup WHERE (userid = ? AND groupid = ?)";
        int userid = 0; Boolean status = false;
        for(JoinedGroup i: group.getListJoined()){
            if(i.getPosition() == JoinedGroup.DELETE_MEMBER){
                userid = i.getUser().getId();
            } else if(i.getPosition() == JoinedGroup.ADMIN_LEAVE_GROUP){
                userid = i.getUser().getId();
                status = true;
            }
        }
        if(!status){
            try{
                PreparedStatement ps = con.prepareStatement(sql2);
                ps.setInt(1, userid);
                ps.setInt(2, group.getId());
 
                ps.executeUpdate();
            }catch(Exception e){
                e.printStackTrace();
                return false;
            }
        } else{
            int idNewAdmin = 0;
            for(JoinedGroup i: group.getListJoined()){
                if(i.getUser().getId()!=userid && i.getPosition()==JoinedGroup.IS_MEMBER){
                    idNewAdmin = i.getUser().getId();
                    break;
                }
            }
            try{
                PreparedStatement ps1 = con.prepareStatement(sql1);
                ps1.setInt(1, JoinedGroup.IS_ADMIN);
                ps1.setInt(2, idNewAdmin);
                ps1.setInt(3, group.getId());
                ps1.executeUpdate();
                
                PreparedStatement ps2 = con.prepareStatement(sql2);
                ps2.setInt(1, userid);
                ps2.setInt(2, group.getId());
                ps2.executeUpdate();
            }catch(Exception e){
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
    
    public boolean addMember(Group group){
        String sql1 = "INSERT INTO gamemario.tbljoinedgroup(userid, groupid, position) VALUES(?,?,?)";
        String sql2 = "UPDATE gamemario.tbljoinedgroup SET position = ? WHERE (userid = ? AND groupid = ?)";
        int userid = 0;
        boolean update = false, add = false;
        for(JoinedGroup i: group.getListJoined()){
            if(i.getPosition() == JoinedGroup.UPDATE_MEMBER){
                userid = i.getUser().getId();
                update = true;
            } else if(i.getPosition() == JoinedGroup.ADD_MEMBER){
                userid = i.getUser().getId();
                add = true;
            }
        }
        if(add){
            try{
                PreparedStatement ps = con.prepareStatement(sql1);
                ps.setInt(1, userid);
                ps.setInt(2, group.getId());
                ps.setInt(3, JoinedGroup.IS_MEMBER);
 
                ps.executeUpdate();
            }catch(Exception e){
                e.printStackTrace();
                return false;
            }
        }
        if(update){
            try{
                PreparedStatement ps = con.prepareStatement(sql2);
                ps.setInt(1, JoinedGroup.IS_MEMBER);
                ps.setInt(2, userid);
                ps.setInt(3, group.getId());
 
                ps.executeUpdate();
            }catch(Exception e){
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
    
    public boolean editGroup(Group group){
        String sql = "UPDATE gamemario.tblgroup SET name = ?, description = ? WHERE id = ?";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, group.getName());
            ps.setString(2, group.getDescription());
            ps.setInt(3, group.getId());
 
            ps.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
