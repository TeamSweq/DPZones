package io.github.teamsweq.dpzones;

import io.github.teamsweq.dpzones.classes.Archer;
import io.github.teamsweq.dpzones.classes.Heavy;
import io.github.teamsweq.dpzones.classes.Medic;
import io.github.teamsweq.dpzones.classes.Soldier;
import io.github.teamsweq.dpzones.classes.Spectate;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DPZones extends JavaPlugin implements Listener {
	private static final List<Class<? extends ZonesClass>> clazzes;
	private static final DyeColor[] teamColors = new DyeColor[]{
		DyeColor.RED, 
		DyeColor.BLUE
	};
	static{
		clazzes = new ArrayList<Class<? extends ZonesClass>>();
		clazzes.add(Heavy.class);
		clazzes.add(Soldier.class);
		clazzes.add(Medic.class);
		clazzes.add(Archer.class);
		clazzes.add(Spectate.class);
	}
	@Override
	public void onEnable(){
		this.getServer().getPluginManager().registerEvents(this, this);
		ClassManager.init(this);
		for(Class<? extends ZonesClass> clazz: clazzes){
			ClassManager.registerClass(clazz, this);
		}
		for(Player player: this.getServer().getOnlinePlayers()){
			autoAssign(player);
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		if(sender instanceof Player){
			if(command.getName().equalsIgnoreCase("team")){
				if(args.length>0){
					DyeColor color = DyeColor.valueOf(args[0].toUpperCase());
					if(color!=null){
						Teams.assignTeam(color, (Player)sender);
						sender.sendMessage(ChatColor.AQUA + "You are now on team: "+color.toString().toLowerCase());
						return true;
					}
				}
			}
			Class<? extends ZonesClass> clazz = ClassManager.getClass(command.getName());
			if(clazz!=null){
				ClassManager.assignClass(((Player) sender), clazz);
				sender.sendMessage(ChatColor.AQUA + "You are now a(n) " + command.getName().toLowerCase() + "!");
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void onDisable() {
		//TODO
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event){
		event.getDrops().clear();
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event){
		ClassManager.resetClass(event.getPlayer());
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		autoAssign(event.getPlayer());
	}
	
	@EventHandler
	public void onFood(FoodLevelChangeEvent event){
		if(ClassManager.getClass((Player)event.getEntity())!=null){
			event.setCancelled(true);
		}
	}
	
	public void autoAssign(Player player){
		Teams.assignTeam(getLowestPlayerTeam(), player);
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
