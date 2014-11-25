package io.github.teamsweq.dpzones;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {
	
	DPZones plugin;
	
	public DeathListener(DPZones instance)
	  {
	    this.plugin = instance;
	  }
	
	@EventHandler(priority = EventPriority.HIGH)
	public void playerDeath(PlayerDeathEvent event) {
		
		final Player player = event.getEntity();
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){ 
			public void run() {
		    if(player.isDead())
		        player.setHealth(20);
		}});
		
//		Entity player = event.getEntity();
//		if (player instanceof Player) {
//		Player p = (Player) player;
//		double damage = event.getDamage();
//		double pHealth = p.getHealth();
//			if (pHealth - damage <= 0) {
//				event.setCancelled(true);
//				Heavy.heavyClass(p);
//			}
	}
}
