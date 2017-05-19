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
                PreparedStatement q = connection.prepareStatement("INSERT INTO serveurmc(uuid,coins,grade) VALUES (?,?,?)");
                q.setString(1, player.getUniqueId().toString());
                q.setInt(2, 100);
                q.setInt(3, Rank.JOUEUR.getPower());
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
            PreparedStatement q = connection.prepareStatement("SELECT uuid FROM serveurmc WHERE uuid = ?");
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
   
    public int getBalance(Player player){
        //SELECT
       
        try {
            PreparedStatement q = connection.prepareStatement("SELECT coins FROM serveurmc WHERE uuid = ?");
            q.setString(1, player.getUniqueId().toString());
 
            int balance = 0;
            ResultSet rs = q.executeQuery();
           
            while(rs.next()){
                balance = rs.getInt("coins");
            }
           
            q.close();
           
            return balance;
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
        return 0;
    }
   
    public void addMoney(Player player,int amount){
        //UPDATE
       
        int balance = getBalance(player);
        int newbalance = balance + amount;
       
        try {
            PreparedStatement rs = connection.prepareStatement("UPDATE serveurmc SET coins = ? WHERE uuid = ?");
            rs.setInt(1, newbalance);
            rs.setString(2, player.getUniqueId().toString());
            rs.executeUpdate();
            rs.close();
   
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
    }
   
    public void removeMoney(Player player,int amount){
        //UPDATE
       
        int balance = getBalance(player);
        int newbalance = balance - amount;
       
        if(newbalance <= 0){
        	player.sendMessage("§cTu n'a pas assez de coins !");
            return;
        }
       
        try {
            PreparedStatement rs = connection.prepareStatement("UPDATE serveurmc SET coins = ? WHERE uuid = ?");
            rs.setInt(1, newbalance);
            rs.setString(2, player.getUniqueId().toString());
            rs.executeUpdate();
            rs.close();
   
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
    }

    
    public void setRank(Player player, Rank rank){
        
        try {
            PreparedStatement rs = connection.prepareStatement("UPDATE serveurmc SET grade = ? WHERE uuid = ?");
            rs.setInt(1, rank.getPower());
            rs.setString(2, player.getUniqueId().toString());
            rs.executeUpdate();
            rs.close();
 
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
    }
   
    public Rank getRank(Player player){
       
       
        try {
            PreparedStatement q = connection.prepareStatement("SELECT grade FROM serveurmc WHERE uuid = ?");
            q.setString(1, player.getUniqueId().toString());
 
            int power = 0;
            ResultSet rs = q.executeQuery();
           
            while(rs.next()){
                power = rs.getInt("grade");
            }
           
            q.close();
           
            return Rank.powerToRank(power);
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
        return Rank.JOUEUR;
    }
    
    
 
}



