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
import java.util.Collections;
import java.util.Comparator;
import model.UserStat;

/**
 *
 * @author hoang
 */
public class RankDAO extends DAO{
    
    public RankDAO() {
        super();
    }
    
    public ArrayList<UserStat> statistics(){
        ArrayList<UserStat> result = new ArrayList<UserStat>();
        String sql = "SELECT tbluser.id, tbluser.email, tbluser.password, tbluser.name, tbluser.gender, tbluser.dateofbirth,"+
            " tbluser.description, winrate, COUNT(gameid) AS ? FROM gamemario.tbluser, gamemario.tblplayedgame,"+
            " (SELECT userid, AVG(result) AS ? FROM gamemario.tblplayedgame GROUP BY tblplayedgame.userid) AS stat"+
            " WHERE tbluser.id = tblplayedgame.userid AND stat.userid = tbluser.id AND tblplayedgame.result = ?"+
            " GROUP BY tblplayedgame.userid";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "winnumber");
            ps.setString(2, "winrate");
            ps.setInt(3, 1);
            ResultSet rs = ps.executeQuery();
 
            while(rs.next()){
                UserStat us = new UserStat();
                us.setId(rs.getInt("id"));
                us.setEmail(rs.getString("email"));
                us.setPassword(rs.getString("password"));
                us.setName(rs.getString("name"));
                us.setGender(rs.getInt("gender")==0);
                us.setDateOfBirth(rs.getDate("dateofbirth"));
                us.setDescription(rs.getString("description"));
                us.setWinRate(rs.getFloat("winrate"));
                us.setWinNumber(rs.getInt("winnumber"));
                result.add(us);;
            }
        }catch(Exception e){
            e.printStackTrace();
        }   
        Collections.sort(result, new Comparator<UserStat>() {
            @Override
            public int compare(UserStat o1, UserStat o2) {
                return o2.getWinNumber() - o1.getWinNumber();
            }
        });
        Collections.sort(result, new Comparator<UserStat>() {
            @Override
            public int compare(UserStat o1, UserStat o2) {
                float x = o2.getWinRate() - o1.getWinRate();
                if(x>0) return 1;
                if(x<0) return -1;
                return 0;
            }
        });
        return result;
    }
}
