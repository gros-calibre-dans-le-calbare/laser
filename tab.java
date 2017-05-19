package fr.maxx;

import java.lang.reflect.Field;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;



public class tab implements Listener {
   
	List<Entity> entities;
	boolean playerLocated = false;
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		sendTablistHeaderAndFooter(e.getPlayer(),SkyWars.getInstance().getConfig().getString("tab.header") ,SkyWars.getInstance().getConfig().getString("tab.footer"));
	}
	
	public void sendTablistHeaderAndFooter(Player p, String header, String footer) {
		if(header == null) header = "";
		if(footer == null) footer = "";
		
		IChatBaseComponent tabHeader = ChatSerializer.a("{\"text\":\"" + header + "\"}");	
		IChatBaseComponent tabFooter = ChatSerializer.a("{\"text\":\"" + footer + "\"}");
		
		PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter(tabHeader);
		try {
			Field field = headerPacket.getClass().getDeclaredField("b");
			field.setAccessible(true);
			field.set(headerPacket, tabFooter);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			((CraftPlayer)p).getHandle().playerConnection.sendPacket(headerPacket);
		}
	}
	public void locatePlayer(PlayerMoveEvent e){
		Player p = e.getPlayer();
		if(GameState.isState(GameState.GAME)){
		for(int i = 0; i < Bukkit.getOnlinePlayers().size(); i++){
			entities = p.getNearbyEntities(i, 64, i);
			for(Entity ent : entities){
				if(ent instanceof Player){
					playerLocated = true;
					if(playerLocated){
						int distance = (int)p.getLocation().distance(ent.getLocation());
					}
				}
			}
		}
		}
	}


}
