package org.teamsweq.dpzones;

import org.teamsweq.dpzones.classes.Archer;
import org.teamsweq.dpzones.classes.Heavy;
import org.teamsweq.dpzones.classes.Medic;
import org.teamsweq.dpzones.classes.Scout;
import org.teamsweq.dpzones.classes.Soldier;
import org.teamsweq.dpzones.classes.Spectate;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class DPZones extends JavaPlugin implements Listener {
	
	public static DPZones plugin;
	
	private static final List<Class<? extends ZonesClass>> clazzes;
	private static Scoreboard scoreboard;
	private static final DyeColor[] teamColors = new DyeColor[]{
		DyeColor.RED, 
		DyeColor.BLUE
	};
	
	static {
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
		plugin = this; //Do not put anything above this statement
		this.getServer().getPluginManager().registerEvents(this, this);
		ClassManager.init(this);
		for(Class<? extends ZonesClass> clazz: clazzes){
			ClassManager.registerClass(clazz, this);
		}
		setupTeamScoreboard();
		for(Player player: this.getServer().getOnlinePlayers()){
			autoAssign(player);
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		if(sender instanceof Player){
			Player player = (Player)sender;
			if(command.getName().equalsIgnoreCase("team")){
				if(args.length>0){
					DyeColor color = DyeColor.valueOf(args[0].toUpperCase());
					if(color!=null){
						assignTeam(player, color);
						sender.sendMessage(ChatColor.AQUA + "You are now on team: "+color.toString().toLowerCase());
						return true;
					}
				}
			}
			Class<? extends ZonesClass> clazz = ClassManager.getClass(command.getName());
			if(clazz!=null){
				ClassManager.assignClass(player, clazz);
				sender.sendMessage(ChatColor.AQUA + "You are now a(n) " + command.getName().toLowerCase() + "!");
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void onDisable() {
		plugin = null; //Do not put anything below this statement.
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
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event){
		event.getDrops().clear();
		final Player player = event.getEntity();
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){ 
					public void run() {
				    if(player.isDead())
				        player.setHealth(20);
		}});
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event){
		ClassManager.resetClass(event.getPlayer());
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		autoAssign(event.getPlayer());
	}

	public void assignTeam(Player player, DyeColor team){
		if(scoreboard.getPlayerTeam(player)!=null){
			scoreboard.getPlayerTeam(player).removePlayer(player);
		}
		scoreboard.getTeam(team.toString()).addPlayer(player);
		Teams.assignTeam(team, player);
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
}
