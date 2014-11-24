package io.github.teamsweq.dpzones;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class DPZones extends JavaPlugin {
	
	public static DPZones plugin;
	public final FoodListener bl = new FoodListener(this);
	FoodListener foodListener = new FoodListener(this);
	
	@Override
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		this.getCommand("spectate").setExecutor(new Teams(this));
		this.getCommand("switch").setExecutor(new Teams(this));
		this.getCommand("team").setExecutor(new Teams(this)); //Not sure if I set this up properly, for all three commands
		pm.registerEvents(this.foodListener, this);
		getServer().getPluginManager().registerEvents(new Teams(), this);
	}
	
	@Override()
	public void onDisable() {
		
	}
	
}
