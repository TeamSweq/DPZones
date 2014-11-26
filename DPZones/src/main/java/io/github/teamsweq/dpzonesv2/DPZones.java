package io.github.teamsweq.dpzonesv2;

import io.github.teamsweq.dpzones.classes.Heavy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class DPZones extends JavaPlugin {
	@Override
	public void onEnable(){
		ClassManager.registerZonesClass(Heavy.class, this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		if(sender instanceof Player){
			Class<? extends ZonesClass> clazz = ClassManager.getClass(command.getName());
			if(clazz!=null){
				ClassManager.assignClass(((Player) sender), clazz);
				sender.sendMessage("You are now: "+command.getName().toLowerCase());
			}
		}
		return false;
	}
	
	@Override
	public void onDisable() {
		
	}
}
