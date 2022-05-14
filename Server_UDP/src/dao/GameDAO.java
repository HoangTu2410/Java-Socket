/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import static dao.DAO.con;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import model.Game;
import model.PlayedGame;
import model.Character;

/**
 *
 * @author hoang
 */
public class GameDAO extends DAO{

    public GameDAO() {
        super();
    }
    
    public Game saveGame(Game game) {
        String sql = "INSERT INTO gamemario.tblgame(status) VALUES(?)";
        String sql2 = "INSERT INTO gamemario.tblplayedgame(gainedpoint, result, characterid, userid, gameid)"
                + " VALUES(?,?,?,?,?)";
        try{
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, game.getStatus());
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                game.setId(generatedKeys.getInt(1));
            }
            
            PreparedStatement ps1 = con.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
            ps1.setInt(1, 0);
            ps1.setInt(2, 0);
            ps1.setInt(3, 100);
            ps1.setInt(4, game.getPlayer1().getUser().getId());
            ps1.setInt(5, game.getId());
            ps1.executeUpdate();
            ResultSet generatedKeys1 = ps1.getGeneratedKeys();
            if (generatedKeys1.next()) {
                game.getPlayer1().setId(generatedKeys1.getInt(1));
            }
            
            PreparedStatement ps2 = con.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
            ps2.setInt(1, 0);
            ps2.setInt(2, 0);
            ps2.setInt(3, 100);
            ps2.setInt(4, game.getPlayer2().getUser().getId());
            ps2.setInt(5, game.getId());
            ps2.executeUpdate();
            ResultSet generatedKeys2 = ps2.getGeneratedKeys();
            if (generatedKeys2.next()) {
                game.getPlayer2().setId(generatedKeys2.getInt(1));
            }
            
            System.out.println("Lưu thành công");
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return game;
    }
    
    public Character checkCharacter(Character character) {
        String sql = "SELECT id FROM gamemario.tblcharacter WHERE name = ? AND image = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, character.getName());
            ps.setString(2, character.getImage());
             
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                character.setId(rs.getInt("id"));
            }
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        return character;
    }
    
    public boolean saveCharacter(Game game) {
        String sql = "UPDATE gamemario.tblplayedgame SET characterid = ? WHERE userid = ? AND gameid = ?";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, game.getPlayer1().getCharacter().getId());
            ps.setInt(2, game.getPlayer1().getUser().getId());
            ps.setInt(3, game.getId());
            ps.executeUpdate();
            
            ps.setInt(1, game.getPlayer2().getCharacter().getId());
            ps.setInt(2, game.getPlayer2().getUser().getId());
            ps.setInt(3, game.getId());
            ps.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
