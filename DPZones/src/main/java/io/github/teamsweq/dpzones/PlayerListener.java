package io.github.teamsweq.dpzones;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class PlayerListener implements Listener {

	DPZones plugin;
	
	public PlayerListener(DPZones instance)
	  {
	    this.plugin = instance;
	  }
	
	@EventHandler
	public void soldierSword(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if ( (event.getAction() == Action.RIGHT_CLICK_BLOCK) || (event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
			if (player.getItemInHand().getType().equals(Material.IRON_SWORD)) {
				player.setVelocity(new Vector(0,1.15,0));
			}
		}
	}
	
	@EventHandler
	public void fallDamage(EntityDamageEvent event) {
		Entity player = event.getEntity();
		if ( (player instanceof Player) ) {
			if (event.getCause().equals(DamageCause.FALL)) {
				event.setCancelled(true);
			}
		}
	}
	
}
