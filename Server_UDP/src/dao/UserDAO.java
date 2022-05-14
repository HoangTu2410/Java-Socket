/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author hoang
 */
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
 
import model.User;
 
public class UserDAO extends DAO{
     
    public UserDAO() {
        super();
    }
     
    public boolean checkLogin(User user) {
        boolean result = false;
        String sql = "SELECT  id, name, gender, dateofbirth, description FROM "
                + "gamemario.tbluser WHERE email = ? AND password = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
             
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setGender(rs.getInt("gender")==0);
                user.setDateOfBirth(rs.getDate("dateofbirth"));
                user.setDescription(rs.getString("description"));
                result = true;
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public boolean addUser(User user) {
       String sql = "INSERT INTO gamemario.tbluser(email, password, name, gender, dateofbirth) VALUES(?,?,?,?,?)";
        try{
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setInt(4, user.getGender()==true?0:1);
            ps.setDate(5, user.getDateOfBirth());
 
            ps.executeUpdate();
             
//            get id of the new inserted client
//            ResultSet generatedKeys = ps.getGeneratedKeys();
//            if (generatedKeys.next()) {
//                user.setId(generatedKeys.getInt(1));
//            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean editUser(User user){
        String sql = "UPDATE gamemario.tbluser SET email = ?, password =?, name=?, gender=?,"
                + "dateofbirth=?, description=? WHERE id=?";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setInt(4, user.getGender()==true?0:1);
            ps.setDate(5, user.getDateOfBirth());
            ps.setString(6, user.getDescription());
            ps.setInt(7, user.getId());
 
            ps.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public ArrayList<User> searchUser(String key){
        ArrayList<User> result = new ArrayList<User>();
        String sql = "SELECT * FROM gamemario.tbluser WHERE name LIKE ?";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + key + "%");
            ResultSet rs = ps.executeQuery();
 
            while(rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setGender(rs.getInt("gender")==0?true:false);
                user.setDateOfBirth(rs.getDate("dateofbirth"));
                user.setDescription(rs.getString("description"));
                
                result.add(user);
            }
        }catch(Exception e){
            e.printStackTrace();
        }   
        return result;
    }
}