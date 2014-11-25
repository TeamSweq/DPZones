package io.github.teamsweq.dpzones;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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
	
	//makes soldiers fly
	@EventHandler
	public void healingSword(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if(plugin.classListener.getClassID(event.getPlayer()) == ClassListener.SOLDIER_ID) {
				if (event.getPlayer().getItemInHand().getType().equals(Material.IRON_SWORD)) {
					event.getPlayer().setVelocity(new Vector(0,1.15,0));
				}
			}
		}
	}
	
	//turns off fall damage for soldiers
	@EventHandler
	public void fallDamage(EntityDamageEvent event) {
		Entity player = event.getEntity();
		if ( (player instanceof Player) ) {
			if(plugin.classListener.getClassID((Player) player) == ClassListener.SOLDIER_ID) {
				if (event.getCause().equals(DamageCause.FALL)) {
					event.setCancelled(true);
				}
			}
		}
	}
	
	//makes archers able to headshot ppl
	@EventHandler
	public void headshot(EntityDamageByEntityEvent event) {
		if(event.getEntity() instanceof Player) {
			Player defender = (Player) event.getEntity();
			if(event.getDamager() instanceof Arrow) {
				Arrow arrow = (Arrow) event.getDamager();
				if(arrow.getShooter() instanceof Player) {
					Player attacker = (Player) arrow.getShooter();
					if(plugin.classListener.getClassID(attacker) == ClassListener.ARCHER_ID) {
						if(attacker.getLocation().distance(defender.getLocation()) >= 30.0) {
							defender.damage(defender.getHealth(), attacker);
							defender.sendMessage(ChatColor.GOLD + "You were headshotted by " + attacker.getDisplayName() + "!");
							attacker.sendMessage(ChatColor.GOLD + "You headshotted " + defender.getDisplayName() + "!");
						}
					}
				}
			}
		}
	}
}
