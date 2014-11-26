package io.github.teamsweq.dpzonesv2;

import java.util.ArrayList;
import java.util.List;

import io.github.teamsweq.dpzones.classes.Heavy;

import org.bukkit.DyeColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
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
	}
	@Override
	public void onEnable(){
		this.getServer().getPluginManager().registerEvents(this, this);
		for(Class<? extends ZonesClass> clazz: clazzes){
			ClassManager.registerZonesClass(clazz, this);
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
					}
				}
			}
			Class<? extends ZonesClass> clazz = ClassManager.getClass(command.getName());
			if(clazz!=null){
				ClassManager.assignClass(((Player) sender), clazz);
				sender.sendMessage("You are now: "+command.getName().toLowerCase());
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void onDisable() {
		
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		Teams.assignTeam(getLowestPlayerTeam(), event.getPlayer());
		ClassManager.assignClass(event.getPlayer(), clazzes.get(0));
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
