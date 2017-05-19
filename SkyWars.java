package fr.maxx;

import java.awt.Menu;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Weather;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import com.avaje.ebean.validation.Length;
import com.avaje.ebeaninternal.server.autofetch.Statistics;

import net.gravenilvec.events.util.Locations;
import net.maxx.coffre.random;
import net.maxx.events.EventsManager;
import net.maxx.events.Win;
import net.maxx.events.death;
import net.maxx.events.playerjoin;
import net.maxx.gamemanage.PreGame;
import net.maxx.gamemanage.startgame;
import net.maxx.kit.KitMenu;
import net.maxx.kit.Kits;
import net.maxx.kit.KitsContents;
import net.maxx.scenario.aide;
import net.maxx.scenario.scenarManager;
import net.maxx.scenario.scenarMenu;
import net.maxx.scenario.commands.BuildCommand;
import net.maxx.scenario.commands.backpackcommand;
import net.maxx.scenario.commands.cutcleanc;
import net.maxx.scenario.commands.cutcleanc2;
import net.maxx.scenario.commands.deleteblockcommand;
import net.maxx.scenario.commands.jumpcommand;
import net.maxx.scenario.commands.noarmorcommand;
import net.maxx.scenario.commands.noswordcommands;
import net.maxx.scenario.events.BackPacks;
import net.maxx.scenario.events.BuildEvent;
import net.maxx.scenario.events.FireballListener;
import net.maxx.scenario.events.PlayerMove1;
import net.maxx.scenario.events.PlayerToggleFly;
import net.maxx.scenario.events.cutcleane;
import net.maxx.scenario.events.cutcleane2;
import net.maxx.scenario.events.kinge;
import net.maxx.scenario.events.noarmor;
import net.maxx.scenario.events.nosworld;
import net.maxx.scenario.events.regren;
import net.maxx.scenario.events.time;
import net.maxx.timer.GameManager;
import net.maxx.timer.refillcoffre;
import net.maxx.scenario.commands.life;




public class SkyWars extends JavaPlugin implements Listener{
	
	public static ArrayList<Player> build = new ArrayList<>();
	public static ArrayList<Player> jump = new ArrayList<>();
	public ArrayList<Player> PlayerList = new ArrayList<>();
	
	String enabled = ChatColor.GREEN + "Fireball Arrows Is Now Enabled!";
	public static scenarManager instancee;
	public static int life = 1;
    public SqlConnection sql;
	public HashMap<Player, Kits> Kits= new HashMap<>();
	public KitsContents kitsContents = new KitsContents();
	public ArrayList<Player> playersList = new ArrayList<>();
	public static SkyWars instance;
	public static String prefix = "§8[§7SkyWars§8] ";
	public void onEnable(){
		
		sql = new SqlConnection("jdbc:mysql://","localhost","stats-skywars","root","serv");
		sql.connection();
		
	    Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		
		if(!getConfig().contains("timer.lobby-game")){
		getConfig().set("timer.lobby-game", 30);
		getConfig().set("timer.pregame-time", 8);
		getConfig().set("timer.refill-time", 120);
		getConfig().set("timer.deathmatch-time", 600);
		getConfig().set("tab.header", "Default header message in Config.yml");
		getConfig().set("tab.footer", "Default footer message in Config.yml");
		
		getConfig().set("location.spawn.x", 0);
		getConfig().set("location.spawn.z", 0);
		getConfig().set("location.spawn.y", 40);
		getConfig().set("location.spawn.WorldName", "world");
		
		getConfig().set("location.cage1.x", 0);
		getConfig().set("location.cage1.y", 0);
		getConfig().set("location.cage1.z", 0);
		
		getConfig().set("location.cage2.x", 0);
		getConfig().set("location.cage2.y", 0);
		getConfig().set("location.cage2.z", 0);
		
		getConfig().set("location.cage3.x", 0);
		getConfig().set("location.cage3.y", 0);
		getConfig().set("location.cage3.z", 0);
		
		getConfig().set("location.cage4.x", 0);
		getConfig().set("location.cage4.y", 0);
		getConfig().set("location.cage4.z", 0);
		
		getConfig().set("location.cage5.x", 0);
		getConfig().set("location.cage5.y", 0);
		getConfig().set("location.cage5.z", 0);
		
		getConfig().set("location.cage6.x", 0);
		getConfig().set("location.cage6.y", 0);
		getConfig().set("location.cage6.z", 0);
		
		getConfig().set("location.cage1.message", "your message %position%");
		getConfig().set("location.cage2.message", "your message %position%");
		getConfig().set("location.cage3.message", "your message %position%");
		getConfig().set("location.cage4.message", "your message %position%");
		getConfig().set("location.cage5.message", "your message %position%");
		getConfig().set("location.cage6.message", "your message %position%");
		
		getConfig().set("Player.minimum-for-start", 2);
		
		getConfig().set("message.command.setminplayer", "you have set the minimum player at ");
		getConfig().set("message.command.help.line1", "&a-----");
		getConfig().set("message.command.help.line2", "You need change the config.yml");
		getConfig().set("message.command.help.line3", "&a-----");
		getConfig().set("message.command.help.line4", "command: /setcage, /setminplayer");
		getConfig().set("message.command.help.line5", "command: /help, /openbackpacks, /aide, /setlobby ");
		saveConfig();
		}
		ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
		console.sendMessage("§a=-=-=-=-==-=-=-==-=");
		console.sendMessage("§cThe ultimSkyWars");
		console.sendMessage("§cVersion 2.4");
		console.sendMessage("§edev: CKLO_Doxa");
		console.sendMessage("§a=-=-=-=-=-=-=-=-=-=");
		instance = this;
		GameState.setState(GameState.LOBBY);
		new random();
		new KitMenu();
		new Locations();
		new EventsManager(this).registerEvents();
		PluginManager pm = Bukkit.getPluginManager();
		Bukkit.getPluginManager().registerEvents(new giveitems(this), this);
		Bukkit.setWhitelist(false);
	    console.sendMessage("§aLe fichier config est g§n§r§");
		
		pm.registerEvents(new aide(), this);
		pm.registerEvents(this, this);
		pm.registerEvents(new backpackcommand(), this);
        pm.registerEvents(new scenarMenu(), this);
    	pm.registerEvents( new BuildEvent(), this);
        pm.registerEvents(new cutcleanc(), this);
        pm.registerEvents(new aide(), this);
        pm.registerEvents(new cutcleane(), this);
        pm.registerEvents(new cutcleanc2(), this);
        pm.registerEvents(new cutcleane2(), this);
        pm.registerEvents(new noarmor(), this);
        pm.registerEvents(new kinge(), this);
        pm.registerEvents(new noarmorcommand(), this);
        pm.registerEvents(new time(), this);
        pm.registerEvents(new regren(), this);
        pm.registerEvents(new FireballListener(null), this);
        pm.registerEvents(new PlayerMove1(), this);
        pm.registerEvents(new PlayerToggleFly(), this);
        pm.registerEvents(new deleteblockcommand(), this);
        PluginCommand lobby2 = getCommand("help");
        lobby2.setExecutor(new commands(this));
        PluginCommand lobby3 = getCommand("hub");
        lobby3.setExecutor(new commands(this));
        PluginCommand title = getCommand("title");
        title.setExecutor(new commands(this));
        PluginCommand cage = getCommand("setcage");
        cage.setExecutor(this);
        PluginCommand lobby = getCommand("setlobby");
        lobby.setExecutor(new tploc(this));
        PluginCommand stat = getCommand("stats");
        stat.setExecutor(this);
        PluginCommand noarmor2_command = getCommand("db");
		noarmor2_command.setExecutor( new deleteblockcommand());
		PluginCommand noarmor_command = getCommand("broadcast");
		noarmor_command.setExecutor( new commands(this));
		PluginCommand noarmor4_command = getCommand("setminplayer");
		noarmor4_command.setExecutor(new commands(this));
		PluginCommand start_command = getCommand("startgame");
		start_command.setExecutor( new commands(this));
		PluginCommand fire_command = getCommand("fireworks");
		fire_command.setExecutor( new commands(this));
		PluginCommand build_command = getCommand("build");
		PluginCommand aide_command = getCommand("aide");
		PluginCommand cutcleanc_command = getCommand("cutclean");
		PluginCommand cutcleanc2_command = getCommand("cutclean2");
		PluginCommand jump2_command = getCommand("jump2");
		PluginCommand fire2_command = getCommand("firebow");
		fire2_command.setExecutor(new kinge());
		jump2_command.setExecutor(new jumpcommand());
		aide_command.setExecutor( new aide());
		build_command.setExecutor( new BuildCommand() );	
		build_command.setDescription("§aPour activer ou d§sactiver la persmission pour build !");
	    PluginCommand time_command = getCommand("time");
	    time_command.setExecutor( new time() ); 
	    PluginCommand regen_command = getCommand("regen");
	    regen_command.setExecutor( new regren() ); 
	    PluginCommand life_command = getCommand("life");
	    life_command.setExecutor(new life());
		cutcleanc_command.setExecutor( new cutcleanc());
		cutcleanc2_command.setExecutor( new cutcleanc2());
		PluginCommand noarmor_command1 = getCommand("noarmor");
		noarmor_command1.setExecutor( new noarmorcommand());
		PluginCommand back_command1 = getCommand("backpacks");
		back_command1.setExecutor( new backpackcommand());
		Bukkit.getWorlds().get(0).setDifficulty(Difficulty.PEACEFUL);
		Bukkit.getWorlds().get(0).setWeatherDuration(66666666);
        pm.registerEvents(new noswordcommands(), this);
        pm.registerEvents(new nosworld(), this);
        PluginCommand ns_command = getCommand("ns");
		ns_command.setExecutor( new noswordcommands());

		PluginCommand backp_command11 = getCommand("openbackpacks");
		backp_command11.setExecutor(this);
		
		
		
		
		
		
		
	}
	
	
	
	@EventHandler
    public void onServerListPing(ServerListPingEvent e) {
		if(GameState.isState(GameState.LOBBY) || GameState.isState(GameState.PREGAME )){
        e.setMotd(prefix + "§6Waiting...");
		}
		if(GameState.isState(GameState.GAME)){
			e.setMotd(prefix + "§aPlay !");
		}
		if(GameState.isState(GameState.FINISH)){
			e.setMotd(prefix + "§cFinish !");
		}
    }
	
	
	public static SkyWars getInstance(){
		return instance;
	}
	

	@Override
    public void onLoad() {
    }
	
	
	@Override
	public void onDisable(){
	  ((Plugin) getServer()).getConfig().getConfigurationSection("backpacks.").set("backpacks.", null);
	  saveConfig();
     sql.disconnect();
		super.onDisable();
	}
	


	
 public static HashMap<UUID, Inventory> backpacks = new HashMap<UUID, Inventory>();
     
     @EventHandler
     public void onPlayerJoin2(PlayerJoinEvent e) {
    	 Player p = e.getPlayer();
    	 sql.createAccount(p);
    	 
    	 
    	 
    	 
    	 
    	 
    	 
    	 
    	 
    	 
             Inventory inv = Bukkit.getServer().createInventory(e.getPlayer(), InventoryType.CHEST, "§asac a dos !");
             backpacks.put(e.getPlayer().getUniqueId(), inv);
             World w = Bukkit.getServer().getWorld("world");
             e.getPlayer().teleport(new Location(w, SkyWars.getInstance().getConfig().getInt("location.spawn.x"),  SkyWars.getInstance().getConfig().getInt("location.spawn.y"),  SkyWars.getInstance().getConfig().getInt("location.spawn.z")));
     }
    


 	
	 private void saveItem(ConfigurationSection section, ItemStack itemStack) {
         section.set("type", itemStack.getType().name());
         section.set("amount", itemStack.getAmount());
         // Save more information.
 }

 private ItemStack loadItem(ConfigurationSection section) {
         return new ItemStack(Material.valueOf(section.getString("type")), section.getInt("amount"));
         // Load more information.
 }
 
 @Override
 public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
         if (!(sender instanceof Player)) {
                 sender.sendMessage(ChatColor.RED + "La console n'a pas de sac.");
                 return true;
         }
        
         Player p = (Player) sender;
         if (cmd.getName().equalsIgnoreCase("openbackpacks")) {
        if(backpackcommand.back == 1){
        	 if(GameState.isState(GameState.GAME)){
                 p.openInventory(backpacks.get(p.getUniqueId()));
        	 }else{
        		 p.sendMessage(prefix + "La partie n'a pas encore commenc§e !");
        	 }
         }else{
         	p.sendMessage(prefix + "§cLe scenario backpacks n'est pas activ§ !");
        }
 		
        }
         
         if(cmd.getName().equalsIgnoreCase("setcage")){
  			if(args.length == 0){
  				p.sendMessage("§cUsage: /setcage <1-6>");
  			}

  			if(args.length == 1){ 
  				if(args[0].equalsIgnoreCase("1")){
  				getConfig().set("location.cage1.x", p.getLocation().getX());
  				getConfig().set("location.cage1.y", p.getLocation().getY());
  				getConfig().set("location.cage1.z", p.getLocation().getZ());
  				String pos =  p.getLocation().getX() +", "+ p.getLocation().getY()+ ", " + p.getLocation().getZ();
  				p.sendMessage(prefix + getConfig().getString("location.cage1.message").replace("%position%", pos).replace("&", "§"));
  				saveConfig();
  			}else if(args[0].equalsIgnoreCase("2")){
  				getConfig().set("location.cage2.x", p.getLocation().getX());
  				getConfig().set("location.cage2.y", p.getLocation().getY());
  				getConfig().set("location.cage2.z", p.getLocation().getZ());
  				String pos =  p.getLocation().getX() +", "+ p.getLocation().getY()+ ", " + p.getLocation().getZ();
  				p.sendMessage(prefix + getConfig().getString("location.cage2.message").replace("%position%", pos).replace("&", "§"));
  				saveConfig();
  			}else if(args[0].equalsIgnoreCase("3")){
  				getConfig().set("location.cage3.x", p.getLocation().getX());
  				getConfig().set("location.cage3.y", p.getLocation().getY());
  				getConfig().set("location.cage3.z", p.getLocation().getZ());
  				String pos =  p.getLocation().getX() +", "+ p.getLocation().getY()+ ", " + p.getLocation().getZ();
  				p.sendMessage(prefix + getConfig().getString("location.cage3.message").replace("%position%", pos).replace("&", "§"));
  				saveConfig();
  			}else if(args[0].equalsIgnoreCase("4")){
  				getConfig().set("location.cage4.x", p.getLocation().getX());
  				getConfig().set("location.cage4.y", p.getLocation().getY());
  				getConfig().set("location.cage4.z", p.getLocation().getZ());
  				String pos =  p.getLocation().getX() +", "+ p.getLocation().getY()+ ", " + p.getLocation().getZ();
  				p.sendMessage(prefix + getConfig().getString("location.cage4.message").replace("%position%", pos).replace("&", "§"));
  				saveConfig();
  			}else if(args[0].equalsIgnoreCase("5")){
  				getConfig().set("location.cage5.x", p.getLocation().getX());
  				getConfig().set("location.cage5.y", p.getLocation().getY());
  				getConfig().set("location.cage5.z", p.getLocation().getZ());
  				String pos =  p.getLocation().getX() +", "+ p.getLocation().getY()+ ", " + p.getLocation().getZ();
  				p.sendMessage(prefix + getConfig().getString("location.cage5.message").replace("%position%", pos).replace("&", "§"));
  				saveConfig();
  			}else if(args[0].equalsIgnoreCase("6")){
  				getConfig().set("location.cage6.x", p.getLocation().getX());
  				getConfig().set("location.cage6.y", p.getLocation().getY());
  				getConfig().set("location.cage6.z", p.getLocation().getZ());
  				String pos =  p.getLocation().getX() +", "+ p.getLocation().getY()+ ", " + p.getLocation().getZ();
  				p.sendMessage(prefix + getConfig().getString("location.cage6.message").replace("%position%", pos).replace("&", "§"));
  				saveConfig();
  			}else{
  				p.sendMessage(prefix +getConfig().getString("message.command.cage.error-max").replace("&", "§"));
  				
  			}
  				
  				
  			}else{
  				p.sendMessage(prefix + "§cTu doit mettre qu'un chiffre !");
  			}
  		}
        
         return true;
 }


 
 public void onReload() {
     this.saveConfig();
 }

 public void loadConfig() {
     if (!this.getDataFolder().exists()) {
         this.getDataFolder().mkdir();
     }
 }

 
	public static Map<Player, ScoreboardSign> boards = new HashMap<>();
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		
		Player player = event.getPlayer();
		
		for(Entry<Player, ScoreboardSign> sign : boards.entrySet()){
			sign.getValue().setLine(4, "" + Bukkit.getOnlinePlayers().size());
		}
		
		ScoreboardSign scoreboards = new ScoreboardSign(player, "§6[§eSkyWars§6]");
		scoreboards.create();
		scoreboards.setLine(0, "§ePlayer: §7"+ player.getName());
		scoreboards.setLine(1, "§eYour Ping: §7" + ((CraftPlayer) player).getHandle().ping);
		scoreboards.setLine(2, "§c ");
		scoreboards.setLine(3, "§eTimer:");
		scoreboards.setLine(4, "§7??:??" );
		scoreboards.setLine(5, "§a ");
		scoreboards.setLine(6, "§ePlayer Online:");
		scoreboards.setLine(7, "§7" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers());
		scoreboards.setLine(8, "§d ");
		scoreboards.setLine(9, "§eserveur.ip.fr");
		scoreboards.setLine(10, "§eKit: §7Aucun");
		scoreboards.setLine(11, "§eserveur.ip.fr");
		boards.put(player, scoreboards);
		
		
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event){
		Player player = event.getPlayer();
		
		for(Entry<Player, ScoreboardSign> sign : boards.entrySet()){
			sign.getValue().setLine(4, "" + (Bukkit.getOnlinePlayers().size() - 1));
		}
		
		if(boards.containsKey(player)){
			boards.get(player).destroy();
		}
		
	}
	
	public void menus(Player p) {

	Inventory StatsInv = Bukkit.createInventory((InventoryHolder)null, (int)54, (String)"§e§lStats");

    ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
    SkullMeta player = (SkullMeta)skull.getItemMeta();
    player.setOwner(p.getName());
    player.setDisplayName("\u00a77\u00a7lPlayer: \u00a7a" + p.getName());
    skull.setItemMeta((ItemMeta)player);
    StatsInv.setItem(13, skull);
    ItemStack Kills = new ItemStack(Material.GOLD_SWORD);
    ItemMeta KillsMeta = Kills.getItemMeta();
    KillsMeta.setDisplayName("§c§l§nKills: §e" + sql.getkill(p));
    Kills.setItemMeta(KillsMeta);
    StatsInv.setItem(20, Kills);
    ItemStack win = new ItemStack(Material.GOLDEN_CARROT);
    ItemMeta winM = win.getItemMeta();
    winM.setDisplayName("§a§l§nTotal Win: §e" + sql.getwin(p));
    win.setItemMeta(winM);
    StatsInv.setItem(22, win);
    ItemStack Deaths = new ItemStack(351, 1, (short) 1);
    ItemMeta DeathsMeta = Deaths.getItemMeta();
    DeathsMeta.setDisplayName("§c§l§nDeaths: §e" + sql.getmort(p));
    Deaths.setItemMeta(DeathsMeta);
    StatsInv.setItem(24, Deaths);
    ItemStack BlocksMined = new ItemStack(Material.DIAMOND_PICKAXE);
    ItemMeta MinedMeta = BlocksMined.getItemMeta();
    MinedMeta.setDisplayName("§b§l§nBlocks breaked: §e" + sql.getbreak(p));
    BlocksMined.setItemMeta(MinedMeta);
    StatsInv.setItem(29, BlocksMined);
    ItemStack Placed = new ItemStack(Material.STONE);
    ItemMeta PlacedMeta = Placed.getItemMeta();
    PlacedMeta.setDisplayName("§6§l§nBlocks Placed: §e" + sql.getplaced(p));
    Placed.setItemMeta(PlacedMeta);
    StatsInv.setItem(33, Placed);
    ItemStack Join = new ItemStack(Material.CHEST);
    ItemMeta JoinMeta = Join.getItemMeta();
    JoinMeta.setDisplayName("§c§l§nTotal Game Played: §e" + sql.getgameplay(p));
    Join.setItemMeta(JoinMeta);
    StatsInv.setItem(38, Join);
    ItemStack damage = new ItemStack(Material.DIAMOND_SWORD);
    ItemMeta damageM = damage.getItemMeta();
    damageM.setDisplayName("§4§l§nTotal damage Taken: §e" + sql.getdamage(p));
    damage.setItemMeta(damageM);
    StatsInv.setItem(40, damage);
    
    ItemStack damage1 = new ItemStack(Material.ARROW);
    ItemMeta damageM1 = damage1.getItemMeta();
    damageM1.setDisplayName("§eExit Menu");
    damage1.setItemMeta(damageM1);
    StatsInv.setItem(31, damage1);
    

    
    ItemStack g = new ItemStack(160, 1, (short) 1);
    ItemMeta gM = g.getItemMeta();
    gM.setDisplayName(" ");
    g.setItemMeta(gM);
    StatsInv.setItem(0, g);
    ItemStack g1 = new ItemStack(160, 1, (short) 1);
    ItemMeta gM1 = g1.getItemMeta();
    gM1.setDisplayName(" ");
    g1.setItemMeta(gM1);
    StatsInv.setItem(1, g1);
    ItemStack g11 = new ItemStack(160, 1, (short) 1);
    ItemMeta gM11 = g11.getItemMeta();
    gM11.setDisplayName(" ");
    g11.setItemMeta(gM11);
    StatsInv.setItem(2, g11);
    ItemStack g2 = new ItemStack(160, 1, (short) 1);
    ItemMeta gM2 = g2.getItemMeta();
    gM2.setDisplayName(" ");
    g2.setItemMeta(gM2);
    StatsInv.setItem(3, g2);
    ItemStack g3 = new ItemStack(160, 1, (short) 1);
    ItemMeta gM3 = g3.getItemMeta();
    gM3.setDisplayName(" ");
    g3.setItemMeta(gM3);
    StatsInv.setItem(4, g3);
    ItemStack g4 = new ItemStack(160, 1, (short) 1);
    ItemMeta gM4 = g.getItemMeta();
    gM4.setDisplayName(" ");
    g.setItemMeta(gM);
    StatsInv.setItem(5, g);
    ItemStack g41 = new ItemStack(160, 1, (short) 1);
    ItemMeta gM41 = g41.getItemMeta();
    gM41.setDisplayName(" ");
    g41.setItemMeta(gM41);
    StatsInv.setItem(6, g41);
    ItemStack g31 = new ItemStack(160, 1, (short) 1);
    ItemMeta gM31 = g31.getItemMeta();
    gM31.setDisplayName(" ");
    g31.setItemMeta(gM31);
    StatsInv.setItem(7, g31);
    ItemStack g311 = new ItemStack(160, 1, (short) 1);
    ItemMeta gM311 = g311.getItemMeta();
    gM311.setDisplayName(" ");
    g311.setItemMeta(gM311);
    StatsInv.setItem(8, g311);
    ItemStack g97 = new ItemStack(160, 1, (short) 1);
    ItemMeta gM97 = g97.getItemMeta();
    gM97.setDisplayName(" ");
    g97.setItemMeta(gM97);
    StatsInv.setItem(45, g97);
    ItemStack g119 = new ItemStack(160, 1, (short) 1);
    ItemMeta gM19 = g119.getItemMeta();
    gM19.setDisplayName(" ");
    g119.setItemMeta(gM19);
    StatsInv.setItem(46, g119);
    ItemStack g115 = new ItemStack(160, 1, (short) 1);
    ItemMeta gM115 = g115.getItemMeta();
    gM115.setDisplayName(" ");
    g115.setItemMeta(gM115);
    StatsInv.setItem(47, g115);
    ItemStack g24 = new ItemStack(160, 1, (short) 1);
    ItemMeta gM24 = g24.getItemMeta();
    gM24.setDisplayName(" ");
    g24.setItemMeta(gM24);
    StatsInv.setItem(48, g24);
    ItemStack g36 = new ItemStack(160, 1, (short) 1);
    ItemMeta gM36 = g36.getItemMeta();
    gM36.setDisplayName(" ");
    g36.setItemMeta(gM36);
    StatsInv.setItem(49, g36);
    ItemStack g411 = new ItemStack(160, 1, (short) 1);
    ItemMeta gM411 = g411.getItemMeta();
    gM411.setDisplayName(" ");
    g.setItemMeta(gM411);
    StatsInv.setItem(50, g411);
    ItemStack g414 = new ItemStack(160, 1, (short) 1);
    ItemMeta gM414 = g414.getItemMeta();
    gM414.setDisplayName(" ");
    g414.setItemMeta(gM414);
    StatsInv.setItem(51, g414);
    ItemStack g312 = new ItemStack(160, 1, (short) 1);
    ItemMeta gM312 = g312.getItemMeta();
    gM312.setDisplayName(" ");
    g312.setItemMeta(gM312);
    StatsInv.setItem(52, g312);
    ItemStack g31111 = new ItemStack(160, 1, (short) 1);
    ItemMeta gM31111 = g31111.getItemMeta();
    gM31111.setDisplayName(" ");
    g31111.setItemMeta(gM31111);
    StatsInv.setItem(53, g31111);
    
    p.openInventory(StatsInv);
    
}
	
	
	
	
	@EventHandler
    public void clickInventory(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        ItemStack current = e.getCurrentItem();
       
        if(inv.getName().equalsIgnoreCase("§e§lStats")){
        	if(current.getType() == Material.ARROW){
	    		  p.closeInventory();
	    	   }
        	e.setCancelled(true);
        	
        }else{
        	e.setCancelled(true);
        }
        }
	
	@EventHandler
    public void onInteractItem(PlayerInteractEvent e){
            Player p = e.getPlayer();
            if(e.getItem() != null
                            && e.getItem().getType() == Material.ANVIL
                            && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)){
            	menus(p);
            }
	}


	
	

@EventHandler
public void onPlayerJoin1(PlayerJoinEvent e) {
    Player p = e.getPlayer();
    int pmfs = getConfig().getInt("Player.minimum_for_start");
	 if(SkyWars.getInstance().playersList.size() == pmfs){
		 new startgame();
	 }		
	 sql.setgameplayer(p, 1);
    
    
}



@EventHandler
public void onDamage(EntityDamageEvent e){
	
		if(e.getEntity() instanceof Player){
			if(DamageCause.ENTITY_ATTACK != null){
			Player p = (Player) e.getEntity();
        	int damage = (int) p.getLastDamage();
        	sql.setdamage(p, damage);
		}
		}
	
}


@EventHandler
public void Placed(BlockPlaceEvent e) {
    Player p = e.getPlayer();
    sql.setplaced(p, 1);
}

@EventHandler
public void okk(PlayerDeathEvent e) {
    Player p = e.getEntity().getPlayer();
    Player killer = e.getEntity().getKiller();
    sql.setmort(p, 1);
    
    sql.setkill(p, 1);
}


@EventHandler
public void Mined(BlockBreakEvent e) {
    Player p = e.getPlayer();
    sql.setbreak(p, 1);
}







public void stats(Player p) {
    Inventory StatsInv = Bukkit.createInventory((InventoryHolder)null, (int)9, (String)"\u00a7a\u00a7lStats");
    int KillsCheck = getConfig().getInt("Stats." + p.getName() + ".Kill");
    int DeathsCheck = getConfig().getInt("Stats." + p.getName() + ".Death");
    int MobKills = getConfig().getInt("Stats." + p.getName() + ".MobKills");
    int Mined = getConfig().getInt("Stats." + p.getName() + ".Mined");
    int JoinCheck = getConfig().getInt("Stats." + p.getName() + ".Joins");
    int PlacedCheck = getConfig().getInt("Stats." + p.getName() + ".Placed");
    int EatCheck = getConfig().getInt("Stats." + p.getName() + ".Eaten");
    ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
    SkullMeta player = (SkullMeta)skull.getItemMeta();
    player.setOwner(p.getName());
    player.setDisplayName("\u00a77\u00a7lPlayer: \u00a7a" + p.getName());
    skull.setItemMeta((ItemMeta)player);
    StatsInv.setItem(0, skull);
    ItemStack Kills = new ItemStack(Material.ARROW);
    ItemMeta KillsMeta = Kills.getItemMeta();
    KillsMeta.setDisplayName("\u00a7c\u00a7lKills: \u00a77" + KillsCheck);
    Kills.setItemMeta(KillsMeta);
    StatsInv.setItem(1, Kills);
    ItemStack Deaths = new ItemStack(351, 1, (short) 1);
    ItemMeta DeathsMeta = Deaths.getItemMeta();
    DeathsMeta.setDisplayName("\u00a7a\u00a7lDeaths: \u00a77" + DeathsCheck);
    Deaths.setItemMeta(DeathsMeta);
    StatsInv.setItem(2, Deaths);
    ItemStack egg = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.SKELETON.ordinal());
    SkullMeta eggp = (SkullMeta)egg.getItemMeta();
    eggp.setDisplayName("\u00a76\u00a7lMob Kills: \u00a77" + MobKills);
    egg.setItemMeta((ItemMeta)eggp);
    StatsInv.setItem(3, egg);
    ItemStack Food = new ItemStack(Material.COOKIE);
    ItemMeta FoodMeta = Food.getItemMeta();
    FoodMeta.setDisplayName("\u00a79\u00a7lFood Eaten: \u00a77" + EatCheck);
    Food.setItemMeta(FoodMeta);
    StatsInv.setItem(4, Food);
    ItemStack BlocksMined = new ItemStack(Material.DIAMOND_SPADE);
    ItemMeta MinedMeta = BlocksMined.getItemMeta();
    MinedMeta.setDisplayName("\u00a7d\u00a7lBlocks Broken: \u00a77" + Mined);
    BlocksMined.setItemMeta(MinedMeta);
    StatsInv.setItem(5, BlocksMined);
    ItemStack Placed = new ItemStack(Material.STONE);
    ItemMeta PlacedMeta = Placed.getItemMeta();
    PlacedMeta.setDisplayName("\u00a73\u00a7lBlocks Placed: \u00a77" + PlacedCheck);
    Placed.setItemMeta(PlacedMeta);
    StatsInv.setItem(6, Placed);
    ItemStack Join = new ItemStack(Material.CHEST);
    ItemMeta JoinMeta = Join.getItemMeta();
    JoinMeta.setDisplayName("\u00a7f\u00a7lTotal Joins: \u00a77" + JoinCheck);
    Join.setItemMeta(JoinMeta);
    StatsInv.setItem(7, Join);
    ItemStack Reset = new ItemStack(Material.WOOD_AXE);
    ItemMeta ResetMeta = Reset.getItemMeta();
    ResetMeta.setDisplayName("\u00a7e\u00a7lReset Your Stats \u00a77(Right-click)");
    ArrayList<String> ResetLore = new ArrayList<String>();
    ResetLore.add("\u00a77Right-click to \u00a7ereset \u00a77your \u00a7estats");
    ResetMeta.setLore(ResetLore);
    Reset.setItemMeta(ResetMeta);
    StatsInv.setItem(8, Reset);
    p.openInventory(StatsInv);
}

}


