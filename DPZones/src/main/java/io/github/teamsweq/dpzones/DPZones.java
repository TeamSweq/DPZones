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
		pm.registerEvents(this.foodListener, this);
		getServer().getPluginManager().registerEvents(new Teams(), this);
	}
	
	@Override()
	public void onDisable() {
		
	}
	
}
