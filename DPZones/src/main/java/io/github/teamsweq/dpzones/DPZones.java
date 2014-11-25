package io.github.teamsweq.dpzones;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class DPZones extends JavaPlugin {
	
	public static DPZones plugin;
	
	public final FoodListener fl = new FoodListener(this);
	FoodListener foodListener = new FoodListener(this);
	
	public final TeamListener tl = new TeamListener(this);
	TeamListener teamListener = new TeamListener(this);
	
	public final PlayerListener pl = new PlayerListener(this);
	PlayerListener playerListener = new PlayerListener(this);
	
	@Override
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		this.getCommand("spectate").setExecutor(new Teams(this));
		this.getCommand("switch").setExecutor(new Teams(this));
		this.getCommand("team").setExecutor(new Teams(this));
		this.getCommand("join").setExecutor(new Teams(this));
		this.getCommand("classes").setExecutor(new Classes(this));
		this.getCommand("heavy").setExecutor(new Heavy(this));
		this.getCommand("archer").setExecutor(new Archer(this));
		this.getCommand("medic").setExecutor(new Medic(this));
		pm.registerEvents(this.foodListener, this);
		pm.registerEvents(this.teamListener, this);
		pm.registerEvents(this.playerListener, this);
	}
	
	@Override()
	public void onDisable() {
		
	}
	
}
