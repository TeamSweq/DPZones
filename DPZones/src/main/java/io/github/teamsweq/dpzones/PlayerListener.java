package io.github.teamsweq.dpzones;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class PlayerListener implements Listener {

	DPZones plugin;
	
	public PlayerListener(DPZones instance)
	  {
	    this.plugin = instance;
	  }
	
	@EventHandler
	public void healingSword(PlayerInteractEvent event) {
		if ( (event.getAction() == Action.RIGHT_CLICK_BLOCK) || (event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
			if (event.getPlayer().getItemInHand().getType().equals(Material.GOLD_SWORD)) {
				event.getPlayer().setVelocity(new Vector(0,3,0));
			}
		}
	}
	
}
