package fr.maxx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;
 
public class SqlConnection {
   
    private Connection connection;
    private String urlbase,host,database,user,pass;
   
    public SqlConnection(String urlbase, String host, String database, String user, String pass) {
        this.urlbase = urlbase;
        this.host = host;
        this.database = database;
        this.user = user;
        this.pass = pass;
    }
   
    public void connection(){
        if(!isConnected()){
            try {
                connection = DriverManager.getConnection(urlbase + host + "/" + database, user, pass);
                System.out.println("Connexion au serveur mysql reussi !");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
   
    public void disconnect(){
        if(isConnected()){
            try {
                connection.close();
                System.out.println("§cconnected off");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
   
    public boolean isConnected(){
        return connection != null;
    }
    
    
    public void createAccount(Player player){
        if(!hasAccount(player)){
            //INSERT
           
            try {
                PreparedStatement q = connection.prepareStatement("INSERT INTO stat(idplayer,mort,kills,win,break,placed,gameplay,damage) VALUES (?,?,?,?,?,?,?,?)");
                q.setString(1, player.getUniqueId().toString());
                q.setInt(2, 0);
                q.setInt(3, 0);
                q.setInt(4, 0);
                q.setInt(5, 0);
                q.setInt(6, 0);
                q.setInt(7, 0);
                q.setInt(8, 0);
                q.execute();
                q.close();
                System.out.println("account crée pour le joueurs " + player.getName());
            } catch (SQLException e) {
                e.printStackTrace();
            }
           
           
        }
    }
   
    public boolean hasAccount(Player player){
        //SELECT
       
        try {
            PreparedStatement q = connection.prepareStatement("SELECT idplayer FROM stat WHERE idplayer = ?");
            q.setString(1, player.getUniqueId().toString());
            ResultSet resultat = q.executeQuery();
            boolean hasAccount = resultat.next();
            q.close();
            return hasAccount;
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
        return false;
    }
   
    public int getmort(Player player){
        //SELECT
       
        try {
            PreparedStatement q = connection.prepareStatement("SELECT mort FROM stat WHERE idplayer = ?");
            q.setString(1, player.getUniqueId().toString());
 
            int mort = 0;
            ResultSet rs = q.executeQuery();
           
            while(rs.next()){
                mort = rs.getInt("mort");
            }
           
            q.close();
           
            return mort;
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
        return 0;
    }
    
 
    public int getkill(Player player){
        //SELECT
       
        try {
            PreparedStatement q = connection.prepareStatement("SELECT kills FROM stat WHERE idplayer = ?");
            q.setString(1, player.getUniqueId().toString());
 
            int mort = 0;
            ResultSet rs = q.executeQuery();
           
            while(rs.next()){
                mort = rs.getInt("kills");
            }
           
            q.close();
           
            return mort;
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
        return 0;
    }
    
 
    
    public int getgameplay(Player player){
        //SELECT
       
        try {
            PreparedStatement q = connection.prepareStatement("SELECT gameplay FROM stat WHERE idplayer = ?");
            q.setString(1, player.getUniqueId().toString());
 
            int mort = 0;
            ResultSet rs = q.executeQuery();
           
            while(rs.next()){
                mort = rs.getInt("gameplay");
            }
           
            q.close();
           
            return mort;
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
        return 0;
    }
    
 
    
    public int getwin(Player player){
        //SELECT
       
        try {
            PreparedStatement q = connection.prepareStatement("SELECT win FROM stat WHERE idplayer = ?");
            q.setString(1, player.getUniqueId().toString());
 
            int mort = 0;
            ResultSet rs = q.executeQuery();
           
            while(rs.next()){
                mort = rs.getInt("win");
            }
           
            q.close();
           
            return mort;
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
        return 0;
    }
    
 
    
    public int getplaced(Player player){
        //SELECT
       
        try {
            PreparedStatement q = connection.prepareStatement("SELECT placed FROM stat WHERE idplayer = ?");
            q.setString(1, player.getUniqueId().toString());
 
            int mort = 0;
            ResultSet rs = q.executeQuery();
           
            while(rs.next()){
                mort = rs.getInt("placed");
            }
           
            q.close();
           
            return mort;
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
        return 0;
    }
    
 
    public int getbreak(Player player){
        //SELECT
       
        try {
            PreparedStatement q = connection.prepareStatement("SELECT break FROM stat WHERE idplayer = ?");
            q.setString(1, player.getUniqueId().toString());
 
            int mort = 0;
            ResultSet rs = q.executeQuery();
           
            while(rs.next()){
                mort = rs.getInt("break");
            }
           
            q.close();
           
            return mort;
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
        return 0;
    }  
 
    public int getdamage(Player player){
        //SELECT
       
        try {
            PreparedStatement q = connection.prepareStatement("SELECT damage FROM stat WHERE idplayer = ?");
            q.setString(1, player.getUniqueId().toString());
 
            int mort = 0;
            ResultSet rs = q.executeQuery();
           
            while(rs.next()){
                mort = rs.getInt("damage");
            }
           
            q.close();
           
            return mort;
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
        return 0;
    } 
    public void setgameplayer(Player player,int amount){
        //UPDATE
       
        int balance = getgameplay(player);
        int newbalance = balance + amount;
       
        try {
            PreparedStatement rs = connection.prepareStatement("UPDATE stat SET gameplay = ? WHERE idplayer = ?");
            rs.setInt(1, newbalance);
            rs.setString(2, player.getUniqueId().toString());
            rs.executeUpdate();
            rs.close();
   
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
    }
    
    
    public void setkill(Player player,int amount){
        //UPDATE
       
        int balance = getkill(player);
        int newbalance = balance + amount;
       
        try {
            PreparedStatement rs = connection.prepareStatement("UPDATE stat SET kills = ? WHERE idplayer = ?");
            rs.setInt(1, newbalance);
            rs.setString(2, player.getUniqueId().toString());
            rs.executeUpdate();
            rs.close();
   
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
    }
    
    public void setmort(Player player,int amount){
        //UPDATE
       
        int balance = getmort(player);
        int newbalance = balance + amount;
       
        try {
            PreparedStatement rs = connection.prepareStatement("UPDATE stat SET mort = ? WHERE idplayer = ?");
            rs.setInt(1, newbalance);
            rs.setString(2, player.getUniqueId().toString());
            rs.executeUpdate();
            rs.close();
   
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
    }
    
    public void setwin(Player player,int amount){
        //UPDATE
       
        int balance = getwin(player);
        int newbalance = balance + amount;
       
        try {
            PreparedStatement rs = connection.prepareStatement("UPDATE stat SET win = ? WHERE idplayer = ?");
            rs.setInt(1, newbalance);
            rs.setString(2, player.getUniqueId().toString());
            rs.executeUpdate();
            rs.close();
   
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
    }
    
    public void setplaced(Player player,int amount){
        //UPDATE
       
        int balance = getplaced(player);
        int newbalance = balance + amount;
       
        try {
            PreparedStatement rs = connection.prepareStatement("UPDATE stat SET placed = ? WHERE idplayer = ?");
            rs.setInt(1, newbalance);
            rs.setString(2, player.getUniqueId().toString());
            rs.executeUpdate();
            rs.close();
   
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
    }
    
    public void setbreak(Player player,int amount){
        //UPDATE
       
        int balance = getbreak(player);
        int newbalance = balance + amount;
       
        try {
            PreparedStatement rs = connection.prepareStatement("UPDATE stat SET break = ? WHERE idplayer = ?");
            rs.setInt(1, newbalance);
            rs.setString(2, player.getUniqueId().toString());
            rs.executeUpdate();
            rs.close();
   
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
    }
    
    public void setdamage(Player player,int amount){
        //UPDATE
       
        int balance = getdamage(player);
        int newbalance = balance + amount;
       
        try {
            PreparedStatement rs = connection.prepareStatement("UPDATE stat SET damage = ? WHERE idplayer = ?");
            rs.setInt(1, newbalance);
            rs.setString(2, player.getUniqueId().toString());
            rs.executeUpdate();
            rs.close();
   
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
    }

}



