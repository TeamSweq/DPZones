package io.github.teamsweq.dpzones;

import io.github.teamsweq.dpzones.classes.Archer;
import io.github.teamsweq.dpzones.classes.Heavy;
import io.github.teamsweq.dpzones.classes.Medic;
import io.github.teamsweq.dpzones.classes.Soldier;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
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
				sender.sendMessage(ChatColor.AQUA + "You are now a " + command.getName().toLowerCase() + "!");
				return true;
			}
			
			if ( (command.getName().equalsIgnoreCase("class"))||(command.getName().equalsIgnoreCase("classes"))||(command.getName().equalsIgnoreCase("kit"))||(command.getName().equalsIgnoreCase("kits")) ) {
				if (sender instanceof Player) {
					sender.sendMessage(ChatColor.GRAY + "Free Classes: /Archer /Heavy /Medic /Soldier");
				} else {
					sender.sendMessage("Only players can choose classes!");
					return false;
				}
			}
			
			if (command.getName().equalsIgnoreCase("heavy")) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					Heavy.onAssign(player);
				} else {
					sender.sendMessage("Only players can choose classes!");
					return false;
				}
			}
			
			if (command.getName().equalsIgnoreCase("archer")) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					Archer.onAssign(player);
				} else {
					sender.sendMessage("Only players can choose classes!");
					return false;
				}
			}
			
			if (command.getName().equalsIgnoreCase("soldier")) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					Soldier.onAssign(player);
				} else {
					sender.sendMessage("Only players can choose classes!");
					return false;
				}
			}
			
			if (command.getName().equalsIgnoreCase("medic")) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					Medic.onAssign(player);
				} else {
					sender.sendMessage("Only players can choose classes!");
					return false;
				}
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
