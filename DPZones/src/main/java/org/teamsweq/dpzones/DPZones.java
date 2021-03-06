package org.teamsweq.dpzones;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.teamsweq.dpzones.classes.Archer;
import org.teamsweq.dpzones.classes.Heavy;
import org.teamsweq.dpzones.classes.Medic;
import org.teamsweq.dpzones.classes.Scout;
import org.teamsweq.dpzones.classes.Soldier;
import org.teamsweq.dpzones.classes.Spectate;

public class DPZones extends JavaPlugin implements Listener {
	private static final List<Class<? extends ZonesClass>> clazzes;
	private static Scoreboard scoreboard;
	private static List<Zone> zones;
	private static final DyeColor[] teamColors = new DyeColor[]{
		DyeColor.RED, 
		DyeColor.BLUE
	};
	
	static {
		zones = new ArrayList<Zone>();
		clazzes = new ArrayList<Class<? extends ZonesClass>>();
		clazzes.add(Heavy.class);
		clazzes.add(Soldier.class);
		clazzes.add(Medic.class);
		clazzes.add(Archer.class);
		clazzes.add(Spectate.class);
		clazzes.add(Scout.class);
	}
	
	@Override
	public void onEnable(){
		this.getServer().getPluginManager().registerEvents(this, this);
		ClassManager.init(this);
		for(Class<? extends ZonesClass> clazz: clazzes){
			ClassManager.registerClass(clazz, this);
		}
		setupTeamScoreboard();
		for(Player player: this.getServer().getOnlinePlayers()){
			autoAssign(player);
			player.setScoreboard(scoreboard);
		}
		setupZones();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		if(sender instanceof Player){
			Player player = (Player)sender;
			UUID uuid = player.getUniqueId();
			if(command.getName().equalsIgnoreCase("team")){
				if(args.length>0){
					DyeColor color = DyeColor.valueOf(args[0].toUpperCase());
					int lowest = 0;
					if(color!=null && Teams.getTeamSize(Teams.getTeam(uuid))<=lowest){
						assignTeam(player, color);
						sender.sendMessage(ChatColor.AQUA + "You are now on the " + color.toString().toLowerCase() + " team!");
						return true;
					}
				} else if (args.length == 0) {
					sender.sendMessage(ChatColor.AQUA + "You are currently on the " + Teams.getTeam(uuid).toString().toLowerCase() + " team!");
				}
			}
			Class<? extends ZonesClass> clazz = ClassManager.getClass(command.getName());
			if(clazz!=null){
				ClassManager.assignClass(player, clazz);
				sender.sendMessage(ChatColor.AQUA + "You have chosen the " + command.getName().toLowerCase() + " class!");
				return true;
			}
			if(command.getName().equalsIgnoreCase("reload")){
				this.reloadConfig();
				setupZones();
				sender.sendMessage(ChatColor.GREEN + "Reloaded Config!");
			}
		}
		return false;
	}
	
	@Override
	public void onDisable() {
		//Nothin
	}

	public void setupTeamScoreboard(){
		ScoreboardManager manager = getServer().getScoreboardManager();
		scoreboard = manager.getNewScoreboard();
		for(DyeColor teamColor: teamColors){
			Team team = scoreboard.registerNewTeam(teamColor.toString());
			for(Player player: getServer().getOnlinePlayers()){
				team.addPlayer(player);
			}
			team.setPrefix(ChatColor.valueOf(teamColor.toString()).toString());
			team.setCanSeeFriendlyInvisibles(true);
			team.setAllowFriendlyFire(false);
		}
	}
	
	public void setupZones(){
		zones.clear();
		List<String> names = this.getConfig().getStringList("Zones");
		for(String name: names){
			List<String> blocks = this.getConfig().getStringList(name+".Blocks");
			for(String block: blocks){
				
			}
			List<String> wools = this.getConfig().getStringList(name+".Wools");
			for(String wool: wools){
				
			}
		}
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event){
		Player player = event.getEntity();
		Location spawnLocation = new Location(player.getWorld(), 0, 0, 0);
		Location spawn = new Location(player.getWorld(), 29, 70, 9);
		
		event.getDrops().clear();
		player.setHealth(20);
		if (player.isDead() && isSafe(spawnLocation) == true){
			player.teleport(spawnLocation);
		} else {
			Random r = new Random();
			int x = r.nextInt(200);
			int z = r.nextInt(200);
			spawnLocation.setX(x);
			spawnLocation.setZ(z);
			spawnLocation.setY(player.getWorld().getHighestBlockAt(spawnLocation.getBlockX(), spawnLocation.getBlockZ()).getY()); // Get the Highest Block for a safe spawn
		}
		ClassManager.resetClass(player);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		autoAssign(event.getPlayer());
		event.getPlayer().setScoreboard(scoreboard);
	}

	public void assignTeam(Player player, DyeColor team){
		if(scoreboard.getPlayerTeam(player)!=null){
			scoreboard.getPlayerTeam(player).removePlayer(player);
		}
		scoreboard.getTeam(team.toString()).addPlayer(player);
	}

	public void autoAssign(Player player){
		assignTeam(player, getLowestPlayerTeam());
		ClassManager.assignClass(player, clazzes.get(0));
	}
	
	public DyeColor getLowestPlayerTeam(){
		DyeColor team = null;
		int lowest = 0;
		for(DyeColor color: teamColors){
			if(Teams.getTeamSize(color)<lowest){
				team = color;
				lowest = Teams.getTeamSize(color);
			}
			if(Teams.getTeamSize(color)==lowest){
				if(Math.random()<=0.5D){
					team = color;
				}
			}
		}
		return team;
	}
	
	/**
	 * checks if a certain location is safe to teleport to
	 * @param location The location to check
	 * @return true if it's safe, otherwise false
	 */
	public boolean isSafe(Location location) {
		Block feet = location.getBlock();
		Block head = feet.getRelative(BlockFace.UP);
		Block below = feet.getRelative(BlockFace.DOWN);
		//Blocks the player is allowed to spawn in
		ArrayList<Material> canSpawnIn = new ArrayList<Material>(Arrays.asList(Material.WOOD_DOOR, Material.WOODEN_DOOR, Material.SIGN_POST, 
				Material.WALL_SIGN, Material.STONE_PLATE, Material.WOOD_PLATE, 
				Material.IRON_DOOR_BLOCK, Material.TRAP_DOOR, Material.REDSTONE_LAMP_OFF, 
				Material.DRAGON_EGG, Material.GOLD_PLATE, Material.IRON_PLATE, Material.AIR));
		//Blocks the player isn't allowed to spawn on top of
		ArrayList<Material> cannotSpawnOn = new ArrayList<Material>(Arrays.asList(Material.PISTON_EXTENSION, 
				Material.LEAVES, Material.LEAVES_2, Material.WATER, 
				Material.STATIONARY_WATER, Material.SIGN_POST, Material.WALL_SIGN, 
				Material.STONE_PLATE, Material.WOOD_PLATE, Material.GOLD_PLATE, 
				Material.IRON_PLATE, Material.IRON_DOOR_BLOCK, Material.TRAP_DOOR, 
				Material.WOOL, Material.STATIONARY_LAVA, Material.LAVA, 
				Material.CACTUS, Material.BEACON, Material.AIR));
		if ((feet.getType().isSolid() && !canSpawnIn.contains(feet.getType())) || feet.isLiquid()) {
			return false;
		} else if ((head.getType().isSolid() && !canSpawnIn.contains(below.getType())) || head.isLiquid()) {
			return false;
		} else if (!below.getType().isSolid() || cannotSpawnOn.contains(below.getType()) || below.isLiquid()) {
			return false;
		}
		return true;
	}
}
