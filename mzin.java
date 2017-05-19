package fr.maxx;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.print.attribute.standard.MediaSize.Other;
import javax.swing.text.html.Option;

import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

import com.mysql.jdbc.BalanceStrategy;

import de.slikey.effectlib.math.dQuadraticTransform;
import de.slikey.effectlib.util.ParticleEffect;

import fr.maxx.gadgets.interact;
import fr.maxx.gadgets.laine;
import fr.maxx.menu.menu;
import fr.maxx.menu.menu_boots;
import fr.maxx.menu.menu_boutique;
import fr.maxx.menu.menu_chestplate;
import fr.maxx.menu.menu_gadgets;
import fr.maxx.menu.menu_hat;
import fr.maxx.menu.menu_helmet;
import fr.maxx.menu.menu_jeu;
import fr.maxx.menu.menu_leggins;
import fr.maxx.menu.menu_lobby;
import fr.maxx.menu.menu_options;
import fr.maxx.menu.menu_pets;
import fr.maxx.menu.menu_skywars;
import fr.maxx.menu.menu_trails;
import fr.maxx.other.PetMaker;
import fr.maxx.other.trails;



public class mzin extends JavaPlugin implements Listener {

	private Scoreboard sb;
	public static Team admins, joueurs, vip, staff;
	
    public static HashMap<Player, Integer> cooldownTime;
 
    public static HashMap<Player, BukkitRunnable> cooldownTask;
    public static HashMap<Player, String> cooldowntype;
    
    public static HashMap<Player, Integer> cooldownTimegadgets;
    
    public static HashMap<Player, BukkitRunnable> cooldownTaskgadgets;
	
	int a= 0;
	int i = 0;
	public static mzin instance;
    private ArrayList<Player> hiders = new ArrayList<Player>();

    public SqlConnection sql;
    

	@Override
	public void onEnable(){
		
		
		sql = new SqlConnection("jdbc:mysql://","localhost","serveurmc","root","azerty");
		sql.connection();
		
		
		ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
		
		
		cooldownTime = new HashMap<Player, Integer>();
	    cooldownTask = new HashMap<Player, BukkitRunnable>();
	    cooldowntype = new HashMap<Player, String>();
	    cooldownTimegadgets = new HashMap<Player, Integer>();
	    cooldownTaskgadgets = new HashMap<Player, BukkitRunnable>();
		instance = this;
		console.sendMessage("§a=-=-=-=-==-=-=-==-=");
		console.sendMessage("§c HUB");
		console.sendMessage("§cVersion 0.1");
		console.sendMessage("§edev: darktek");
		console.sendMessage("§a=-=-=-=-=-=-=-=-=-=");
		
		
		sb = Bukkit.getScoreboardManager().getNewScoreboard();
		
		admins = sb.registerNewTeam("§A_admins");	
		admins.setPrefix("§c[admin] ");
		
		vip = sb.registerNewTeam("§C_vip");
		vip.setPrefix("§2[§a§lVIP§2]§a ");
		
		joueurs = sb.registerNewTeam("§D_joueurs");
		joueurs.setPrefix("§7[joueurs] ");
		staff = sb.registerNewTeam("§B_staff");
		staff.setPrefix("§9[Staff] ");

		
		

		
		
		
		getServer().getWorld("world").setDifficulty(Difficulty.PEACEFUL);
		
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new menu_helmet(), this);
		pm.registerEvents(new menu_chestplate(), this);
		pm.registerEvents(new menu_leggins(), this);
		pm.registerEvents(new menu_boots(), this);
		pm.registerEvents(new chat(), this);
		pm.registerEvents(new menu(), this);
		pm.registerEvents(new menu_options(), this);
		pm.registerEvents(new tab(), this);
		pm.registerEvents(this, this);
		pm.registerEvents(new ping(), this);
		pm.registerEvents(new menu_skywars(), this);
		pm.registerEvents(new jump(), this);
		pm.registerEvents(new menu_lobby(), this);
		pm.registerEvents(new interact(), this);
		
		pm.registerEvents(new menu_trails(), this);
		pm.registerEvents(new trails(), this);
		pm.registerEvents(new menu_gadgets(), this);
		pm.registerEvents(new menu_hat(), this);
		pm.registerEvents(new menu_jeu(), this);
		pm.registerEvents(new PetMaker(), this);
		pm.registerEvents(new menu_pets(), this);
		pm.registerEvents(new menu_boutique(), this);
		pm.registerEvents(new laine(), this);
		

	    
		
		
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		

		Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
			
			@Override
			public void run() {
				
				onfoserv.main1("localhost", 25659);
				for(Player pl : Bukkit.getOnlinePlayers()){
					
					if(!(a == onfoserv.count)){
						if(onfoserv.count <= 16 && onfoserv.count >= 0){
								a = onfoserv.count;
							pl.updateInventory();
							ItemStack regen = new ItemStack(57, onfoserv.count);
							ItemMeta regen2 =  regen.getItemMeta();  
							regen2.setDisplayName("§6§lSkyWars 1vs1 !");
							regen2.setLore(Arrays.asList("§ePour aller faire un skywars", "§eplace: §a" + onfoserv.count + "/" + onfoserv.serv));
							regen.setItemMeta(regen2);
							menu_skywars.menu.setItem(21,  regen);
						}else if(onfoserv.count >= 17 && onfoserv.count <= 19){
							pl.updateInventory();
							ItemStack regen = new ItemStack(41, onfoserv.count);
							ItemMeta regen2 =  regen.getItemMeta();  
							regen2.setDisplayName("§6§lSkyWars 1vs1 !");
							regen2.setLore(Arrays.asList("§ePour aller faire un skywars", "§eplace: §a" + onfoserv.count + "/" + onfoserv.serv));
							regen.setItemMeta(regen2);
							menu_skywars.menu.setItem(21,  regen);
							
						}else if(onfoserv.count == 20){
							ItemStack regen = new ItemStack(Material.REDSTONE_BLOCK, onfoserv.count);
							ItemMeta regen2 =  regen.getItemMeta();  
							regen2.setDisplayName("§6§lSkyWars 1vs1 !");
							regen2.setLore(Arrays.asList("§ePour aller faire un skywars", " §c§lFULL !  §a" + onfoserv.count + "/" + onfoserv.serv));
							regen.setItemMeta(regen2);
							menu_skywars.menu.setItem(21,  regen);
							
						}else if(onfoserv.count == -1){
							ItemStack regen = new ItemStack(Material.REDSTONE_BLOCK, onfoserv.count);
							ItemMeta regen2 =  regen.getItemMeta();  
							regen2.setDisplayName("§6§lSkyWars 1vs1 ! OFF");
							regen2.setLore(Arrays.asList("§ePour aller faire un skywars", " §c§lOFF !"));
							regen.setItemMeta(regen2);
							menu_skywars.menu.setItem(21,  regen);
						}
					}
				
				}
			}
		}, 20, 20);
		
	     
	}
	
	
	public void onDisable(){
		sql.disconnect();
		
	}
	
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		
		
		 if(sender instanceof Player){

		 if(label.equalsIgnoreCase("money")){
	           
	            Player p = (Player)sender;
	           
	            //money --> donner son argent
	            if(args.length == 0){
	                int balance = sql.getBalance(p);
	                p.sendMessage("§7Vous avez : §e" + balance+" §7points");
	            }
	           
	            //money add 50 gravenilvec
	           
	            if(args.length >= 1){
	               
	                //money add
	                if(args[0].equalsIgnoreCase("add")){
	                   
	                    if(args.length == 1 || args.length == 2){
	                        p.sendMessage("Veuillez tapez /money add <montant> <joueur> ");
	                    }
	                   
	                    if(args.length == 3){
	                       
	                        Player cible = Bukkit.getPlayer(args[2]);
	                        if(cible != null){ 
	                            int montant = Integer.valueOf(args[1]);
	                            sql.addMoney(cible, montant);
	                            cible.sendMessage("Vous recevez : " + montant+" points de la part de " + p.getName());
	                            cible.sendMessage("Vous venez d'envoyer : " + montant+" points à " + cible.getName());
	                        }
	                       
	                       
	                    }
	                   
	                }
	               
	                //money remove
	                if(args[0].equalsIgnoreCase("remove")){
	                   
	                    if(args.length == 1 || args.length == 2){
	                        p.sendMessage("Veuillez tapez /money remove <joueur> <montant>");
	                    }
	                   
	                    if(args.length == 3){
	                       
	                        Player cible = Bukkit.getPlayer(args[2]);
	                        if(cible != null){ 
	                            int montant = Integer.valueOf(args[1]);
	                            sql.removeMoney(cible, montant);
	                            cible.sendMessage("Vous recevez : " + montant+" points de la part de " + p.getName());
	                            cible.sendMessage("Vous venez de retirer : " + montant+" points à " + cible.getName());
	                        }
	                       
	                       
	                    }
	                   
	                   
	                }
	               
	            }
	           
	            //money remove gravenilvec 50
	           
	        }
		
		 }
	
		
		
		
		
		
		
		
		
		
		
		Player p = (Player) sender;
		if(label.equalsIgnoreCase("test")){
		
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		
		try{
			
			out.writeUTF("PlayerCounter");
			out.writeUTF("serv1");
			
			
			
		}catch (Exception e){
			e.printStackTrace();
			
		}
		p.sendPluginMessage(this, "BungeeCord", b.toByteArray());
		
		}
		
		
		if(label.equalsIgnoreCase("rename")){
			if(args.length < 1){
				p.sendMessage("§cRename Pets: §4Usage: /rename pets <name>");
			}
			if(args.length >= 1){
				StringBuilder str = new StringBuilder();
 				for(int i = 0; i < args.length; i++){
 					str.append(args[i] + " ");
 				}
				if(str.length() > 17){
					p.sendMessage("§c§lLe name ne peux pas dépassé les 16 characters !");
				}else{
					getConfig().set("options." + p.getUniqueId() + ".namepets", str.toString());
					saveConfig();
					p.sendMessage("§a§lLe nouveau nom de votre pets est " + str.toString().replace("&", "§"));
					
				}
			}
		}
		
	
		return false;
		
		
	}
	
	
	 public boolean onCommand(CommandSender sender, Command command, String label, Rank[] args, String[] args2){
			
			
		 if(sender instanceof Player){
			 
			 if(label.equalsIgnoreCase("setpower")){
				 if(args[1] == null){
					 ((Player) sender).getPlayer().sendMessage("§cUsage: /setpower <joueur, VIP, staff, admin> nom");
				 }else if(args[0] == null){
					 ((Player) sender).getPlayer().sendMessage("§cUsage: /setpower <joueur, VIP, staff, admin> nom du joueur");
				 }else{
	                 Player cible = Bukkit.getPlayer(args2[1]);
					 sql.setRank(((Player) sender).getPlayer(), args[0]);
				 }
				 
				 
			 }
		 }
		return false;
	 }
	
	
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss");
	
	
	
	@EventHandler
	 public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
			String date = sdf.format(new Date());
	     Player p = event.getPlayer();
	     Rank rank = sql.getRank(p);
	     
	     event.setFormat("§8[§7"+date+"§8]" + rank.getName() + p.getName() + " : " + rank.getTag() + event.getMessage());
	     
		
		}
	
	
	
	
	
	
	
	
 
	
	
	public static mzin getInstance(){
		return instance;
	}
	
	public Rank rank2;
	public void removem(Player p, int n){
		if(sql.getRank(p) != rank2.JOUEUR){
			p.sendMessage("§cTu posséde déjà ceci !");
			p.sendMessage("" + sql.getBalance(p));
			
		}
		if(sql.getRank(p) == rank2.JOUEUR){
			int balance = sql.getBalance(p);
			int b2 = balance - n;
			p.sendMessage(b2 + " " + balance);
			
		if(b2 < 0){	
			p.sendMessage("§cTu n'as pas assez d'argents !");
			p.sendMessage("" + b2);
			return;
		}else{
			
			sql.removeMoney(p, n);
			p.sendMessage("" + n);
			sql.setRank(p, rank2.VIP);
			p.sendMessage("§a§lAchat éfféctué avec succé !");
			Fireworks.spawn1RandomFirework(p.getLocation());
			Fireworks.spawn1RandomFirework(p.getLocation());
			Fireworks.spawn1RandomFirework(p.getLocation());
		}
		
	}
	}
	
	public static Map<Player, ScoreboardSign> boards = new HashMap<>();
	
	public Rank rank;
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		

		
		
		
		
		final Player player = event.getPlayer();
		
		sql.createAccount(player);
		
		if(sql.getRank(player) == rank.JOUEUR){
			joueurs.addEntry(player.getName());
		}
		if(sql.getRank(player) == rank.VIP){
  			
  			vip.addEntry(player.getName());
  		}
		
		if(sql.getRank(player) == rank.MODO){
			staff.addEntry(player.getName());
		}
		
		if(sql.getRank(player) == rank.ADMIN){
			admins.addEntry(player.getName());
		}
		
		for(Player p : Bukkit.getOnlinePlayers()){
		player.setScoreboard(sb);
		}

				for(Entry<Player, ScoreboardSign> sign : mzin.getInstance().boards.entrySet()){
					sign.getValue().setLine(7, "§7" + ("§7 " + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers()));
					
				}
				
		
		final ScoreboardSign scoreboards = new ScoreboardSign(player, "§e§lMearadaMC");
		scoreboards.create();
		scoreboards.setLine(0, "§ePlayer: §7"+ player.getName());
		scoreboards.setLine(1, "§eT'on Ping: §7" + ((CraftPlayer) player).getHandle().ping);		
		scoreboards.setLine(2, "§eGrade: " + sql.getRank(player).getName());
		scoreboards.setLine(3, "§c ");
		scoreboards.setLine(4, "§eServeur actuelle:");
		scoreboards.setLine(5, "§7HUB-01");
		scoreboards.setLine(6, "§ePlayer Online:");
		scoreboards.setLine(7, "§7" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers());
		scoreboards.setLine(8, "§d ");
		scoreboards.setLine(9, "§eserveur.ip.fr");
		boards.put(player, scoreboards);	
		
		
		
		
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
			
			@Override
			public void run() {
				i++;
				if(i == 0){
				
				for(Entry<Player, ScoreboardSign> sign : boards.entrySet()){
					scoreboards.setObjectiveName("cc");
				}

				}
				
				if(i == 1){
					
					for(Entry<Player, ScoreboardSign> sign : boards.entrySet()){
						scoreboards.setObjectiveName("§c§lMearadaMC");
					}

					}
				

				if(i == 2){
					
					for(Entry<Player, ScoreboardSign> sign : boards.entrySet()){
						scoreboards.setObjectiveName("§a§lMearadaMC");
					}
					}
				if(i == 3){
					
					for(Entry<Player, ScoreboardSign> sign : boards.entrySet()){
						scoreboards.setObjectiveName("§e§lMearadaMC");
					}
					}
				
				if(i == 4){
					
					for(Entry<Player, ScoreboardSign> sign : boards.entrySet()){
						scoreboards.setObjectiveName("§b§lMearadaMC");
					}
					}

				if(i == 5){
	
	for(Entry<Player, ScoreboardSign> sign : boards.entrySet()){
		scoreboards.setObjectiveName("§6§lMearadaMC");
	}
	i = 0;
	
	
	
	
	}
				
				
				
				
				
				
			}
		}, 0, 5);
		
		
	
		
		
		
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event){
		Player player = event.getPlayer();
		for(Entry<Player, ScoreboardSign> sign : boards.entrySet()){
			sign.getValue().setLine(7, "§7" + (Bukkit.getOnlinePlayers().size() - 1  + "/" + Bukkit.getMaxPlayers()));
		}
		
		if(boards.containsKey(player)){
			boards.get(player).destroy();
		}
		
	}
	
	@EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        
        ItemStack is = new ItemStack(Material.LEATHER);
  		ItemMeta meta = is.getItemMeta();
  		meta.addEnchant(Enchantment.FIRE_ASPECT, 1, false);
  		meta.setDisplayName("§a§lVisibilité");
  		meta.setLore(Arrays.asList("§e§serre a aller dans les ", "differents serveurs"));
  		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES);
  		is.setItemMeta(meta);
  		p.getInventory().setItem(0, is);
        
        for(Player pls : hiders){
            pls.hidePlayer(p);
        }
    }
 
    @EventHandler
    public void onQuit1(PlayerQuitEvent e){
        if(hiders.contains(e.getPlayer())){
            hiders.remove(e.getPlayer());
        }
    }
 
    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(e.getItem() == null)return;
        if(e.getItem().getType() != Material.LEATHER)return;

 
        Player p = e.getPlayer();
        if(hiders.contains(p)){
            for(Player pls :  Bukkit.getOnlinePlayers()){
                p.showPlayer(pls);
            }
            hiders.remove(p);
            p.sendMessage("§a§ltu vois de nouveau les joueurs !");
            p.playSound(p.getLocation(), Sound.NOTE_PLING, 10, 10);
            p.playEffect(p.getLocation(), Effect.FIREWORKS_SPARK, 1);
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30, 5));
        }else{
            for(Player pls : Bukkit.getOnlinePlayers()){
                p.hidePlayer(pls);
            }
            p.sendMessage("§c§lTu ne vois plus les joueurs !");
            p.playSound(p.getLocation(), Sound.NOTE_PLING, 10, 10);

            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30, 5));
            hiders.add(p);
        }
 
    }
    

}
