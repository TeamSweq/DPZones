package com.gmail.jjfent.zones;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Zones extends JavaPlugin {
	
	public static Zones plugin;
	public final FoodHealthListener bl = new FoodHealthListener(this);
	FoodHealthListener foodListener = new FoodHealthListener(this);
	
	@Override
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		this.getCommand("spectate").setExecutor(new Spectate(this));
		this.getCommand("switch").setExecutor(new TeamSwitch(this));
		this.getCommand("team").setExecutor(new TeamSwitch(this));
		this.getCommand("classes").setExecutor(new Classes(this));
		this.getCommand("heavy").setExecutor(new Heavy(this));
		this.getCommand("archer").setExecutor(new Archer(this));
		pm.registerEvents(this.foodListener, this);
		getServer().getPluginManager().registerEvents(new Teams(), this);
	}
	
	@Override()
	public void onDisable() {
		
	}
}
