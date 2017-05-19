package fr.maxx;

import java.lang.reflect.Field;
import java.util.List;

import net.minecraft.server.v1_8_R3.EntityEnderDragon;
import net.minecraft.server.v1_8_R3.EntityWither;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.WorldServer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.inventivetalent.bossbar.BossBar;
import org.inventivetalent.bossbar.BossBarAPI;

public class tab implements Listener {
	   
	List<Entity> entities;
	boolean playerLocated = false;
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		sendTablistHeaderAndFooter(e.getPlayer(), "§aBienvenue sur le serveur §e§lMearadaMC", "§cts: §f12.54.254.32 §c/ site: §fsite.fr");	
		
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
	
	
	

	


}