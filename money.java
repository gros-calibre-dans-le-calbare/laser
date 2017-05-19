package fr.maxx;

import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class money {
	
	static public Rank rank;
	static public SqlConnection sql;

	public static void removem(Player p, int n){
		if(sql.getRank(p) != rank.JOUEUR){
			p.sendMessage("§cTu posséde déjà ceci !");
			
		}
		if(sql.getRank(p) == rank.JOUEUR){
			int balance = sql.getBalance(p);
			balance = balance + n;
			
			
		if(balance < 0){	
			p.sendMessage("§cTu n'as pas assez d'argents !");
			return;
		}else{
			
			sql.removeMoney(p, n);
			sql.setRank(p, rank.VIP);
			p.sendMessage("§a§lAchat éfféctué avec succé !");
			Fireworks.spawn1RandomFirework(p.getLocation());
		}
		
	}
	}
	
}
