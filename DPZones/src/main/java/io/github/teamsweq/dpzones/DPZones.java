package io.github.teamsweq.dpzones;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class DPZones extends JavaPlugin {
	
	public static DPZones plugin;
	public final FoodListener fl = new FoodListener(this);
	FoodListener foodListener = new FoodListener(this);
	public final TeamListener tl = new TeamListener(this);
	TeamListener teamListener = new TeamListener(this);
	
	@Override
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		this.getCommand("spectate").setExecutor(new TeamSwitch(this));
		this.getCommand("switch").setExecutor(new TeamSwitch(this));
		this.getCommand("team").setExecutor(new TeamSwitch(this)); //Not sure if I set this up properly, for all three commands
		pm.registerEvents(this.foodListener, this);
		pm.registerEvents(this.teamListener, this);
	}
	
	@Override()
	public void onDisable() {
		
	}
	
}
